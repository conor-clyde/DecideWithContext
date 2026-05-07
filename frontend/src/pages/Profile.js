import { useEffect, useState } from "react";

export default function Profile() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    async function loadProfile() {
      setLoading(true);
      setError("");
      try {
        const response = await fetch("/api/profile", { credentials: "include" });
        const data = await response.json();
        if (!response.ok) {
          throw new Error(data.error || "Could not load profile");
        }
        setProfile(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
    loadProfile();
  }, []);

  return (
    <section className="profile-page">
      <h1>Profile</h1>
      {loading ? <p>Loading profile...</p> : null}
      {error ? <p className="err">{error}</p> : null}
      {profile ? (
        <div className="panel">
          <p>
            <strong>Email:</strong> {profile.email}
          </p>
          <p>This page is intentionally simple for now.</p>
        </div>
      ) : null}
    </section>
  );
}
