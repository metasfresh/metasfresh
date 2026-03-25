# Testing

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Jest, Storybook, and debugging patterns**

## Unit Tests (Jest)

```bash
# Run all tests
yarn test

# Run tests in watch mode
yarn test:watch

# Generate coverage report
yarn coverage
```

**Test Location**: `src/__tests__/`

**Test File Naming**:
- Component tests: `ComponentName.test.js`
- Utility tests: `utilityName.test.js`

### Example Test

```javascript
import React from 'react';
import { render, screen } from '@testing-library/react';
import MyComponent from '../components/MyComponent';

describe('MyComponent', () => {
  it('renders without crashing', () => {
    render(<MyComponent />);
    expect(screen.getByText('Hello')).toBeInTheDocument();
  });
});
```

## Storybook (Component Development)

```bash
# Start Storybook development server
yarn storybook
# Access: http://localhost:6006

# Build static Storybook
yarn build-storybook
```

**Story Location**: `src/stories/`

## Test Debugging

### Running Single Test

```bash
yarn test ComponentName.test.js
```

### Common Test Issues

1. **Check test setup files**: `test_setup/jestSetup.js`
2. **Verify mocks**: `test_setup/fileMock.js`, `test_setup/styleMock.js`
3. **Async timing**: Use proper `waitFor` patterns
4. **Mock config**: Ensure `config` object is mocked if needed

## E2E Tests

E2E tests are in the main repository: `e2e/` folder (not in frontend/).

See root `CLAUDE.md` for Playwright/Cypress testing guidance.

## Code Quality: Linting

**CRITICAL: Always run `yarn lintfix` before committing!**

The CI/CD pipeline runs `yarn lint` during Docker build and **will fail** if there are any linting errors.

```bash
# Check for linting issues (CI runs this)
yarn lint

# Auto-fix linting issues (ALWAYS run before commit/push)
yarn lintfix

# Lint CSS
yarn stylelint
```

**Configuration**:
- JavaScript: `.eslintrc` (ESLint + Prettier)
- CSS: `.stylelintrc`
- Editor: `.editorconfig`

### Pre-commit Workflow

1. Make your changes
2. Run `yarn lintfix` - Auto-fixes formatting
3. Run `yarn lint` - Verify no errors remain
4. Commit and push

### Common Linting Issues

- **Prettier formatting** - Line length, spacing (auto-fixed)
- **PropTypes validation** - Missing prop type definitions
- **React/JSX rules** - Component structure, hook usage

## Debugging Component Issues

1. Check if component is class-based or functional
2. Check Redux DevTools for state changes
3. Verify props passed to component (React DevTools)
4. Check API network calls (Browser DevTools Network tab)
5. Look for console errors/warnings
6. Check if `immutability-helper` is used correctly in reducers

## Test Configuration

### Jest Setup

Key files:
- `test_setup/jestSetup.js` - Global test setup
- `test_setup/fileMock.js` - Mock for static files
- `test_setup/styleMock.js` - Mock for CSS/styles

### Mocking Patterns

```javascript
// Mock axios
jest.mock('axios');

// Mock config
jest.mock('../config', () => ({
  API_URL: 'http://test-api',
  WS_URL: 'http://test-ws'
}));
```
