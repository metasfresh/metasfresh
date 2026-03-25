# Troubleshooting

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Common issues and solutions**

## Development Server Won't Start

**Symptom**: `yarn start` fails

### Port 3000 Already in Use

```bash
# macOS/Linux
lsof -ti:3000 | xargs kill -9

# Windows
netstat -ano | findstr :3000
taskkill /PID <pid> /F
```

Or change port in `server.js`

### Missing Dependencies

```bash
rm -rf node_modules yarn.lock
yarn install
```

### Backend API Not Running

Start backend API on port 8080 first. Check `config.js` points to correct API URL.

### Missing config.js

```bash
cp config.js.dist config.js
```

## Hot Reload Not Working

**Symptom**: Changes don't reflect in browser

1. Hard refresh: Ctrl+Shift+R / Cmd+Shift+R
2. Restart development server
3. Clear browser cache
4. Check webpack configuration
5. Verify `hot: true` in server.js

## API Calls Failing (CORS)

**Symptom**: Network errors in browser console

1. Ensure backend has CORS enabled for `http://localhost:3000`
2. Check `config.js` has correct API URL with `/rest/api` path
3. Verify backend is running and accessible
4. Check browser console for specific CORS error

## Build Fails

**Symptom**: `yarn build-prod` errors

### Linting Errors

```bash
yarn lintfix  # Run this first!
```

### Memory Issues

```bash
NODE_OPTIONS=--max-old-space-size=4096 yarn build-prod
```

### Missing Dependencies

```bash
yarn install
```

### Git Not Available

Affects GitRevisionPlugin - check webpack config.

## Tests Failing

**Symptom**: `yarn test` shows failures

1. Run single test: `yarn test ComponentName.test.js`
2. Check test setup files: `test_setup/jestSetup.js`
3. Verify mocks are correct
4. Check for async timing issues
5. Ensure `config` object is mocked if needed

## WebSocket Connection Issues

**Symptom**: Real-time updates not working

1. Check backend WebSocket endpoint at `/stomp`
2. Verify `WS_URL` in `config.js` (`http://localhost:8080/stomp`)
3. Check browser console for connection errors
4. Ensure firewall allows WebSocket connections
5. Test with SockJS fallback enabled

## Performance Issues

### Bundle Size Analysis

```bash
yarn add -D webpack-bundle-analyzer
yarn build-prod
npx webpack-bundle-analyzer dist/stats.json
```

### Slow Renders

1. Use React DevTools Profiler
2. Check for unnecessary re-renders
3. Use `PureComponent` or `React.memo`
4. Memoize selectors with Reselect

## Common Error Messages

### "Cannot read property 'x' of undefined"

- Check if API response structure changed
- Verify Redux state shape
- Add null checks or optional chaining (`?.`)

### "Minified React error"

Enable React DevTools to see full error:
```javascript
// Add to index.js during development
if (process.env.NODE_ENV !== 'production') {
  require('@babel/polyfill');
}
```

### "Module not found"

```bash
# Verify import path
# Check if module is installed
yarn add <missing-module>
```

## Deployment Issues

### Docker Build Fails

1. Run `yarn lint` locally - fix any errors
2. Check `Dockerfile.frontend` for correct commands
3. Verify Node.js version matches

### Production Config

Ensure `dist/config.js` has:
- Correct production API URL
- `wss://` for WebSocket (not `ws://`)
- Valid Google API key if using maps

## When Debugging

1. **Check component type** - class-based or functional?
2. **Check Redux DevTools** - state changes correct?
3. **Check React DevTools** - props passed correctly?
4. **Check Network tab** - API calls succeeding?
5. **Check Console** - any errors/warnings?
6. **Check immutability-helper** - reducers correct?
