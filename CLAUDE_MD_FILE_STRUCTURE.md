# CLAUDE.md Split File Structure

This document shows the proposed file structure after refactoring, with detailed content distribution for each file.

---

## Directory Structure Overview

```
metasfresh/
├── CLAUDE.md                                    (150 lines) ← Main hub
│
├── backend/
│   ├── CLAUDE.md                                (existing symlink)
│   ├── BUILD_AND_TEST.md                        (150 lines) ← NEW
│   ├── ARCHITECTURE.md                          (200 lines) ← NEW
│   ├── de.metas.async/src/main/java/de/metas/async/
│   │   └── CLAUDE.md                            (existing)
│   └── de.metas.cucumber/
│       └── CLAUDE.md                            (existing)
│
├── frontend/
│   ├── CLAUDE.md                                (300 lines) ← Refactored
│   ├── REDUX_PATTERNS.md                        (250 lines) ← NEW
│   ├── COMPONENTS_AND_WIDGETS.md                (200 lines) ← NEW
│   ├── API_AND_WEBSOCKET.md                     (150 lines) ← NEW
│   └── TROUBLESHOOTING.md                       (200 lines) ← NEW
│
├── e2e/
│   └── frontend-webui/
│       ├── CLAUDE.md                            (250 lines) ← Refactored
│       ├── README.md                            (existing)
│       ├── WIDGET_PATTERNS.md                   (existing)
│       ├── SELECTOR_PATTERNS.md                 (200 lines) ← NEW
│       ├── TIMING_AND_WAITS.md                  (150 lines) ← NEW
│       ├── WEBAPI_VALIDATION.md                 (150 lines) ← NEW
│       └── LESSONS_LEARNED.md                   (200 lines) ← NEW
│
├── infrastructure/
│   └── SETUP_AND_INSTALLATION.md                (150 lines) ← NEW
│
├── workflows/
│   └── GIT_AND_CI.md                            (100 lines) ← NEW
│
├── integrations/
│   └── PLAYWRIGHT_MCP.md                        (250 lines) ← NEW
│
├── syncing/
│   └── CLAUDE_CONFIG_SYNC.md                    (150 lines) ← NEW
│
├── docker-builds/
│   └── CLAUDE.md                                (existing)
│
└── misc/dev-support/docker/infrastructure/
    └── CLAUDE.md                                (existing)
```

---

## File #1: Main CLAUDE.md (150 lines)

**Purpose**: Navigation hub + critical protocols

### Content Distribution

```markdown
# CLAUDE.md (Lines 1-150)

## About metasfresh (10 lines)
- Project description
- 3-tier architecture
- Key technologies

## ⚠️ CRITICAL: ALWAYS Read Subfolder CLAUDE.md First (25 lines)
- Mandatory protocol (KEEP - added today)
- 4-step checklist
- Example workflow
- Self-check question

## Quick Start for New Developers (20 lines) ← NEW
1. Setup Environment → [Setup Guide](infrastructure/SETUP_AND_INSTALLATION.md)
2. Understand Architecture → [Architecture](backend/ARCHITECTURE.md)
3. Read Context Docs → [Subfolder Documentation](#subfolder-documentation)
4. Start Services → Docker commands
5. Choose Your Path:
   - Backend Dev → [Backend CLAUDE.md](backend/CLAUDE.md)
   - Frontend Dev → [Frontend CLAUDE.md](frontend/CLAUDE.md)
   - E2E Testing → [E2E CLAUDE.md](e2e/frontend-webui/CLAUDE.md)

## Project Structure (30 lines) ← NEW
[Visual tree diagram of metasfresh/ structure]
- backend/ - Java Spring Boot (100+ modules)
- frontend/ - React 16 web UI
- e2e/ - Playwright tests
- docker-builds/ - Container definitions
- misc/ - Build tools, infrastructure

## Windows PowerShell Environment (15 lines)
- Summary of key principles
- Link to detailed guide: [Setup & Installation](infrastructure/SETUP_AND_INSTALLATION.md)

## Subfolder Documentation (40 lines)
- Navigation hub to all specialized docs
- When to use each
- **NEW**: Links to extracted files

## Skills (10 lines)
- Brief list with links to details in relevant files

Total: ~145 lines
```

### Cross-References Added

```markdown
**Related Guides**:
- [Backend Build & Test](backend/BUILD_AND_TEST.md)
- [Backend Architecture](backend/ARCHITECTURE.md)
- [Frontend Development](frontend/CLAUDE.md)
- [E2E Testing](e2e/frontend-webui/CLAUDE.md)
- [Git Workflow](workflows/GIT_AND_CI.md)
- [Setup Guide](infrastructure/SETUP_AND_INSTALLATION.md)
```

---

## File #2: backend/BUILD_AND_TEST.md (150 lines)

**Source**: Main CLAUDE.md lines 398-535

### Content Distribution

```markdown
# Backend Build & Test Guide

## Overview (10 lines)
- Build system: Maven
- Test frameworks: JUnit, Cucumber
- Local vs CI builds

## ⚠️ CRITICAL: Use the Skill (15 lines)
- Invoke `metasfresh-compile-and-test` skill
- Why: Handles Maven config, long-running builds
- Don't construct Maven commands manually

## Full Builds - User Approval Required (20 lines)
- Takes 20-25 minutes
- Requires periodic check-ins
- Cannot run autonomously
- Ask user first

## Maven Configuration (40 lines)
- settings.xml location
- Git plugin skip flag
- Maven help goal
- Local repository: .m2-local/

## Compilation Workflows (30 lines)
- Individual modules
- Full codebase
- Dependencies only
- Background execution

## Testing (35 lines)
- Unit tests (JUnit)
- Integration tests (Cucumber)
  - Link to: backend/de.metas.cucumber/CLAUDE.md
- Running tests via skill
- Test debugging

Total: ~150 lines
```

---

## File #3: backend/ARCHITECTURE.md (200 lines)

**Source**: Main CLAUDE.md lines 487-535, 943-965

### Content Distribution

```markdown
# Backend Architecture Guide

## System Overview (20 lines)
- 3-tier architecture
- Java 8, Spring Boot
- REST API (metasfresh-webui-api)
- 100+ Maven modules

## Project Structure (40 lines)
[Detailed tree of backend/ directory]

## Module Architecture (50 lines)
- Layered dependency structure:
  - Base layer: de.metas.adempiere.adempiere, de.metas.util
  - Business layer: de.metas.business, de.metas.async
  - Domain layer: contracts, manufacturing, material, payment
  - UI layer: metasfresh-webui-api
  - Integration layer: EDI, REST APIs

## Parent-pom and Common Libraries (30 lines)
- ./misc/parent-pom/pom.xml
- ./misc/de-metas-common
- Both must be installed to .m2-local/

## Key Technologies (30 lines)
- Java 8
- Spring Boot
- Lombok (used extensively)
- PostgreSQL
- RabbitMQ

## Generated Model Classes (20 lines)
- I_* prefix (interfaces)
- X_* prefix (implementations)
- Generated from DB schema

## Lombok Usage (10 lines)
- Widespread use
- Common errors: duplicated methods after merge

Total: ~200 lines
```

---

## File #4: infrastructure/SETUP_AND_INSTALLATION.md (150 lines)

**Source**: Main CLAUDE.md lines 90-107, 476-485

### Content Distribution

```markdown
# Setup & Installation Guide

## Prerequisites (15 lines)
- Windows 11 with PowerShell
- Docker Desktop
- Java 8 JDK
- Maven 3.8+
- Node.js 16+ (for frontend)

## PowerShell Environment Setup (40 lines)
- Core principles
- Common mistakes
- Command reference table
- Example conversions (Unix → PowerShell)
- Command chaining

## Maven Local Repository (30 lines)
- ⚠️ CRITICAL: Workspace-specific
- Location: .m2-local/
- NOT ~/.m2/repository/
- Why: Parallel branch development
- What to do if artifact missing

## Docker Infrastructure (40 lines)
- Located: ./misc/dev-support/docker/infrastructure/
- Scripts:
  - 95_start.sh - Start all services
  - 93_status.sh - Check status
  - 97_stop.sh - Stop all services
- Services: PostgreSQL, RabbitMQ, Elasticsearch
- Link to: misc/dev-support/docker/infrastructure/CLAUDE.md

## First-Time Setup Checklist (25 lines)
1. Clone repository
2. Install dependencies
3. Start Docker infrastructure
4. Build parent-pom and de-metas-common
5. Configure IDE (IntelliJ recommended)
6. Verify setup

Total: ~150 lines
```

---

## File #5: workflows/GIT_AND_CI.md (100 lines)

**Source**: Main CLAUDE.md lines 340-365, 929-941

### Content Distribution

```markdown
# Git Workflow & CI/CD

## Main Branch (10 lines)
- Primary branch: new_dawn_uat
- Branch protection enabled
- No direct pushes allowed

## Feature Branch Workflow (30 lines)
1. Create feature branch
2. Make changes and commit
3. Push and create PR
- Detailed git commands
- Commit message guidelines
- Co-Authored-By: Claude

## Pull Request Creation (20 lines)
- Using gh CLI
- PR template
- Required reviewers
- CI checks

## CI/CD Pipeline (30 lines)
- Location: .github/workflows/cicd.yaml
- Build stages
- Test execution
- Docker image publishing
- Deployment

## Best Practices (10 lines)
- Commit frequency
- Message clarity
- Review process

Total: ~100 lines
```

---

## File #6: integrations/PLAYWRIGHT_MCP.md (250 lines)

**Source**: Main CLAUDE.md lines 536-911

### Content Distribution

```markdown
# Playwright MCP Integration Guide

## Overview (15 lines)
- What is MCP
- When to use
- Prerequisites

## Available Skills (30 lines)
- playwright-create (DEPRECATED)
- playwright-explore
- playwright-test
- When to use each

## MCP Tools Reference (80 lines)
- browser_snapshot (PRIMARY TOOL)
- browser_take_screenshot
- browser_click
- browser_type
- browser_fill_form
- browser_evaluate
- browser_run_code
- Console and network inspection tools

## Test Creation Workflow (60 lines)
- Exploration phase
- Test creation phase
- Validation phase
- Step-by-step examples

## Code Patterns (40 lines)
- Create test data via Backend API
- Use Page Object Model
- Prefer robust selectors
- Include automatic error detection

## Debugging with MCP (25 lines)
- Element won't click
- Data won't load
- Test error conditions

Total: ~250 lines
```

---

## File #7: syncing/CLAUDE_CONFIG_SYNC.md (150 lines)

**Source**: Main CLAUDE.md lines 250-338

### Content Distribution

```markdown
# Claude Configuration Sync Guide

## Central Repository (15 lines)
- mf15-ai-dev-support
- Symlink-based system
- Why: Centralized management

## Finding the Location (20 lines)
- Read symlink target
- Example commands
- Extract base path

## Syncing New Files (40 lines)
- Run migrate-new-files.ps1
- Automatic detection
- Move and symlink creation
- Supported file patterns

## Manual Alternative (30 lines)
- When to use
- Step-by-step commands
- Creating symlinks manually

## Committing Changes (30 lines)
- Discover mf15-ai-dev-support location
- Navigate to repository
- Git workflow for Claude files

## Quick Reference (15 lines)
- Common commands
- Troubleshooting

Total: ~150 lines
```

---

## File #8: frontend/CLAUDE.md (Refactored to 300 lines)

**Current**: 1,178 lines
**Target**: 300 lines (extraction to 4 new files)

### Content Distribution

```markdown
# Frontend Development Guide (300 lines)

## Overview (20 lines)
- React 16, Redux
- Technology stack summary
- Project structure

## Quick Start (40 lines)
- Install dependencies
- Run dev server
- Build production
- Run tests
- ⚠️ CRITICAL: Always run yarn lintfix before commit

## Project Structure (30 lines)
[Visual tree of frontend/src/]

## Technology Stack (40 lines)
- Core framework versions
- State management
- UI libraries
- Build tools

## Development Workflow (30 lines)
- Code, test, lint cycle
- Hot reload
- Debugging

## Linting - CRITICAL (30 lines)
- Why it matters (CI will fail)
- yarn lintfix before every commit
- ESLint configuration
- Common issues

## Common Patterns (60 lines)
- Brief overview
- Link to detailed guides:
  - [Redux Patterns](REDUX_PATTERNS.md)
  - [Components & Widgets](COMPONENTS_AND_WIDGETS.md)
  - [API & WebSocket](API_AND_WEBSOCKET.md)

## Troubleshooting (20 lines)
- Quick fixes
- Link to: [Troubleshooting Guide](TROUBLESHOOTING.md)

## Related Documentation (30 lines)
- Navigation to specialized docs

Total: ~300 lines
```

---

## File #9: frontend/REDUX_PATTERNS.md (250 lines)

**Source**: frontend/CLAUDE.md (Redux sections)

### Content Distribution

```markdown
# Redux Patterns Guide

## Overview (15 lines)
- Plain action creators (not redux-actions)
- immutability-helper for reducers
- Reselect for selectors

## Action Pattern (60 lines)
- Simple synchronous actions
- Async actions with Thunk
- Examples from actual codebase
- Dispatching in components

## Reducer Pattern (80 lines)
- Using immutability-helper
- Common operations ($set, $merge, $push, etc.)
- Example reducer from codebase
- Best practices

## Selector Pattern (60 lines)
- Simple selectors
- Memoized selectors (createSelector)
- Cached selectors (createCachedSelector)
- Usage in components

## Best Practices (35 lines)
- Normalized state
- Avoiding derived data
- Performance optimization

Total: ~250 lines
```

---

## File #10: frontend/COMPONENTS_AND_WIDGETS.md (200 lines)

**Source**: frontend/CLAUDE.md (Component sections)

### Content Distribution

```markdown
# Components & Widgets Guide

## Component Patterns (80 lines)
- Class components (PureComponent)
- Functional components with hooks
- When to use each
- Examples from codebase

## Container Pattern (40 lines)
- Redux connected components
- mapStateToProps
- mapDispatchToProps
- Example from codebase

## Widget Pattern (40 lines)
- Input components for forms
- Standard interface
- Example widget

## Dictionary (40 lines)
- MasterWindow
- DocumentList
- Widget types
- Terminology reference

Total: ~200 lines
```

---

## File #11: e2e/frontend-webui/CLAUDE.md (Refactored to 250 lines)

**Current**: 858 lines
**Target**: 250 lines (extraction to 4 new files)

### Content Distribution

```markdown
# E2E Testing Guide (250 lines)

## Overview (20 lines)
- Playwright for desktop web UI
- Multi-language testing
- Language-independent selectors

## ⚠️ CRITICAL: Always Test Before Completing (20 lines)
- Mandatory workflow
- Run tests locally
- Verify pass in multiple languages
- Commands reference

## Quick Start (30 lines)
- Running tests
- Debugging tests
- Headed vs headless mode

## Language-Independent Testing (40 lines)
- Core principle
- Selector priority
- Link to: [Selector Patterns](SELECTOR_PATTERNS.md)

## Multi-Language Test Pattern (30 lines)
- Parameterized tests
- testCases.forEach pattern
- Example template

## Backend API Integration (30 lines)
- Masterdata creation
- Test data isolation
- Link to: [WebAPI Validation](WEBAPI_VALIDATION.md)

## Page Object Model (40 lines)
- Structure
- Template
- Best practices

## Timeout Configuration (20 lines)
- DEFAULT, SLOW, VERY_SLOW
- When to use each

## Related Documentation (20 lines)
- Navigation to specialized docs:
  - [Selector Patterns](SELECTOR_PATTERNS.md)
  - [Timing & Waits](TIMING_AND_WAITS.md)
  - [WebAPI Validation](WEBAPI_VALIDATION.md)
  - [Lessons Learned](LESSONS_LEARNED.md)

Total: ~250 lines
```

---

## File #12: e2e/frontend-webui/SELECTOR_PATTERNS.md (200 lines)

**Source**: e2e/frontend-webui/CLAUDE.md (Selector sections)

### Content Distribution

```markdown
# Selector Patterns Guide

## Language-Independent Principle (20 lines)
- Why it matters
- Multi-language validation

## Selector Priority (40 lines)
1. data-testid (BEST)
2. Database column names in IDs
3. CSS classes
4. Roles with names (CAUTION)
5. Text content (NEVER)
- Examples of each

## When to Add data-testid (40 lines)
- Rule: Can't find language-independent selector
- How to add
  - Identify component
  - Add attribute
  - Semantic naming
  - Rebuild frontend
  - Update test
- Components that should have data-testid

## Using data-cy on Grid Cells (40 lines) ← NEW (Today's learning)
- Pattern: Grid cells have data-cy attributes
- Example from shipment schedule
- When to use
- Number formatting (locale handling)
- ✅ GOOD vs ❌ BAD examples

## Components with data-testid (30 lines)
- ActionButton
- Actions (header)
- Tabs
- ModalButton
- TableFilter
- TableHeader (columns) ← NEW (Today's addition)

## Examples (30 lines)
- Real-world patterns from tests

Total: ~200 lines
```

---

## File #13: e2e/frontend-webui/TIMING_AND_WAITS.md (150 lines)

**Source**: e2e/frontend-webui/CLAUDE.md (Timing sections)

### Content Distribution

```markdown
# Timing & Waits Guide

## Dynamic Form Loading Problem (20 lines)
- Why timing matters
- Symptoms
- Root cause

## Two-Step Wait Pattern (50 lines)
- Step 1: Wait for container
- Step 2: Wait for specific element
- Step 3: Now safe to interact
- Why this works
- Example: Batch entry modal

## Common Containers (20 lines)
- .quick-input-container
- .modal-content
- .input-dropdown-list
- .tab-content

## Timeout Configuration (30 lines)
- DEFAULT_ACTION_TIMEOUT (10s)
- SLOW_ACTION_TIMEOUT (20s)
- VERY_SLOW_ACTION_TIMEOUT (30s)
- When to use each

## Common Pitfalls (30 lines)
- Fixed timeouts unreliable
- Batch entry modal not loading
- Lookup dropdown not appearing
- Solutions for each

Total: ~150 lines
```

---

## File #14: e2e/frontend-webui/WEBAPI_VALIDATION.md (150 lines)

**Source**: e2e/frontend-webui/CLAUDE.md (WebAPI section)

### Content Distribution

```markdown
# WebAPI Validation Guide

## The Problem (30 lines)
- Parent records must be saved first
- Why child records fail
- Common error symptoms

## The Solution (40 lines)
- WebAPI validation pattern
- GET /window/{windowId}/{recordId}
- Response structure:
  - saveStatus
  - validStatus
  - fieldsByName
  - includedTabsInfo

## Workflow Pattern (40 lines)
- Fill parent fields
- Get record ID from URL
- Debug validation (optional)
- Fill remaining mandatory fields
- Wait for record saved
- Wait for tab allows new
- Add child records
- Complete example

## Auto-Fill Behavior (20 lines)
- What triggers auto-fill
- Example: Sales Order after C_BPartner
- When auto-fill doesn't happen

## Key Utilities (20 lines)
- waitForRecordSaved()
- waitForTabAllowsNew()
- debugRecordValidation()
- Common Tab IDs table

Total: ~150 lines
```

---

## File #15: e2e/frontend-webui/LESSONS_LEARNED.md (200 lines)

**Source**: e2e/frontend-webui/CLAUDE.md (Lessons section)

### Content Distribution

```markdown
# E2E Testing Lessons Learned

## HIGH CONFIDENCE (100 lines)
1. Two-step waiting for dynamic forms
2. data-testid mandatory for language independence
3. Batch entry modal timing
4. Database column names provide stable selectors
5. Using data-cy on grid cells ← NEW (Today's learning)

## MEDIUM CONFIDENCE (60 lines)
6. Parameterized tests eliminate duplication
7. Language parameter in test data creation
8. Test failures reveal timing issues
9. Investigate frontend code when selectors fail

## LOW CONFIDENCE (40 lines)
10. CSS classes for structural elements
11. Reduced test timeout improves feedback

Total: ~200 lines
```

---

## Migration Checklist

When implementing this structure:

- [ ] Create all new directories
- [ ] Create all new files with extracted content
- [ ] Update main CLAUDE.md with links
- [ ] Update all internal cross-references
- [ ] Test all links work correctly
- [ ] Verify symlinks in mf15-ai-dev-support
- [ ] Commit changes with descriptive messages
- [ ] Notify team of new structure

---

## Benefits of This Structure

1. **Findability**: Each file has single, clear purpose
2. **Maintainability**: Changes are isolated to specific files
3. **Scalability**: Easy to add new specialized docs
4. **Context Window**: Files fit comfortably in Claude's context
5. **Navigation**: Main file acts as clear hub with quick links
6. **Discoverability**: Logical file names make it easy to find what you need

---

## File Size Validation

| File | Target | Actual After Refactor |
|------|--------|-----------------------|
| Main CLAUDE.md | <150 | ~145 ✓ |
| Frontend CLAUDE.md | <300 | ~300 ✓ |
| E2E CLAUDE.md | <250 | ~250 ✓ |
| All new files | <250 | All ≤250 ✓ |

**Total lines after refactor**: ~3,200 lines (distributed across 15+ files)
**Total lines before refactor**: ~3,500 lines (concentrated in 3 main files)
**Reduction**: ~300 lines (through elimination of duplication)

**Quality score projection**: 72 → **88/100** after refactor
