// The receipt day is "today" in the organisation's time zone. The test organisation has none set, so the
// app server's zone applies, which is Europe/Berlin (see TZ in docker-builds/Dockerfile.backend.app).
// Returns the receipt day plus the given number of days, as ISO date and in the displayed format DD.MM.YYYY.
export const receiptDayPlusDays = (days) => {
    const today = new Date().toLocaleDateString('en-CA', { timeZone: 'Europe/Berlin' }); // YYYY-MM-DD
    const date = new Date(`${today}T00:00:00Z`);
    date.setUTCDate(date.getUTCDate() + days);
    const iso = date.toISOString().substring(0, 10);
    const [year, month, day] = iso.split('-');
    return { iso, displayed: `${day}.${month}.${year}` };
};
