import { useEffect, useState } from "react";

const API = (process.env.REACT_APP_API_ORIGIN || "http://localhost:8080").replace(
  /\/$/,
  ""
);
const COOLDOWN_S = 60;

function parseOptions(text) {
  try {
    const j = JSON.parse(text);
    if (Array.isArray(j) && j.length && j.every((x) => x && typeof x === "object"))
      return j;
  } catch (_) {}
  return null;
}

export default function Home() {
  const [mood, setMood] = useState("");
  const [energy, setEnergy] = useState(5);
  const [minutes, setMinutes] = useState(60);
  const [interests, setInterests] = useState("");
  const [text, setText] = useState("");
  const [status, setStatus] = useState("");
  const [busy, setBusy] = useState(false);
  const [wait, setWait] = useState(0);

  useEffect(() => {
    if (wait <= 0) return undefined;
    const id = setTimeout(() => setWait((w) => w - 1), 1000);
    return () => clearTimeout(id);
  }, [wait]);

  async function onSubmit(e) {
    e.preventDefault();
    if (busy || wait > 0) return;
    const list = interests.split(",").map((s) => s.trim()).filter(Boolean);
    const body = {
      mood: mood.trim() || "unsure",
      energyLevel: Number(energy),
      freeTimeMinutes: Number(minutes),
      interests: list,
    };
    setBusy(true);
    setText("");
    setStatus("");
    try {
      const res = await fetch(`${API}/api/decide`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });
      setText(await res.text());
      setStatus(`${res.status} ${res.statusText}`);
    } catch (err) {
      setStatus("Network error");
      setText(err instanceof Error ? err.message : String(err));
    } finally {
      setBusy(false);
      setWait(COOLDOWN_S);
    }
  }

  const options = text && !busy ? parseOptions(text) : null;
  const first = options?.[0];
  const rest = options?.slice(1) ?? [];

  return (
    <div className="home">
      <div className="hero">
        <h1>What should I do?</h1>
        <p>A little context helps suggest something that fits your time and mood.</p>
      </div>

      <form className="panel" onSubmit={onSubmit}>
        <label>
          Mood
          <input value={mood} onChange={(e) => setMood(e.target.value)} placeholder="e.g. tired, relaxed" />
        </label>
        <div className="split">
          <label>
            Energy (1–10)
            <input type="number" min={1} max={10} value={energy} onChange={(e) => setEnergy(e.target.value)} />
          </label>
          <label>
            Minutes free
            <input type="number" min={5} max={600} value={minutes} onChange={(e) => setMinutes(e.target.value)} />
          </label>
        </div>
        <label>
          Interests
          <input value={interests} onChange={(e) => setInterests(e.target.value)} placeholder="movies, music" />
        </label>
        <button type="submit" disabled={busy || wait > 0}>
          {busy ? "…" : wait > 0 ? `Wait ${wait}s` : "Ask"}
        </button>
      </form>

      <section className="answer">
        {!text && !busy && <p className="hint">Answer appears here.</p>}
        {busy && <p className="hint">One moment…</p>}
        {text && !busy && (
          <>
            {status && !status.startsWith("2") && <p className="err">{status}</p>}
            {first?.title ? (
              <div className="box">
                <div className="hint">Suggestion</div>
                <strong>{first.title}</strong>
                {first.reason && <p>{first.reason}</p>}
                {(first.type || first.duration != null) && (
                  <p className="hint">
                    {[first.type, typeof first.duration === "number" ? `${first.duration} min` : null]
                      .filter(Boolean)
                      .join(" · ")}
                  </p>
                )}
              </div>
            ) : (
              <pre className="box pre">{text}</pre>
            )}
            {rest.length > 0 && (
              <ul className="more">
                {rest.map((x, i) => (
                  <li key={i}>
                    <strong>{x.title}</strong>
                    {x.reason && <span>{x.reason}</span>}
                  </li>
                ))}
              </ul>
            )}
            <details className="dev">
              <summary>Raw</summary>
              <pre>{text}</pre>
            </details>
          </>
        )}
      </section>
    </div>
  );
}
