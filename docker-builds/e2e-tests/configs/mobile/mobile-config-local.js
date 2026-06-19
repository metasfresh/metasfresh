// Local testing config - uses localhost instead of Docker internal hostname
// Used by: docker compose -f compose.yml -f compose-local.yml --profile mobile up
window.config = {
    SERVER_URL: 'http://localhost:8282',
}
