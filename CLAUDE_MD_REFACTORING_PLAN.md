# CLAUDE.md Refactoring Plan

**Current State**: metasfresh/CLAUDE.md is 990 lines (3.3x recommended limit)
**Target State**: Hub file <150 lines + specialized context files
**Timeline**: 3 phases over 4 weeks

---

## Phase 1: Core Split (Week 1) - CRITICAL

### Goal
Reduce main CLAUDE.md from 990 → ~150 lines by extracting major sections to context-specific files.

### Files to Create

#### 1.1 `backend/BUILD_AND_TEST.md` (150 lines)
**Extract from main CLAUDE.md lines 398-535**

**Content**:
- Compiling and Testing section (complete)
- Maven configuration (settings.xml, git-plugin)
- Maven help goal usage
- Critical build warnings
- Full build approval requirements

**Why separate**: Build/test workflows are backend-specific and highly detailed.

**Link from main**:
```markdown
## Compiling and Testing

**USE THE SKILL**: For all compilation and testing tasks, invoke the `metasfresh-compile-and-test` skill.

**See**: [Backend Build & Test Guide](backend/BUILD_AND_TEST.md) for Maven configuration and manual build commands.
```

#### 1.2 `backend/ARCHITECTURE.md` (200 lines)
**Extract from main CLAUDE.md lines 487-535, 943-965**

**Content**:
- Architecture Overview section
- Backend Structure (modular Maven architecture)
- Parent-pom and common libraries
- Module Dependencies (layered structure)
- Key Technologies (backend only)

**Why separate**: Architectural information is reference material, rarely needed during active development.

**Link from main**:
```markdown
## Architecture

metasfresh is a 3-tier ERP system with 100+ backend modules. For detailed architecture:

**See**: [Backend Architecture Guide](backend/ARCHITECTURE.md)
```

#### 1.3 `infrastructure/SETUP_AND_INSTALLATION.md` (150 lines)
**Extract from main CLAUDE.md lines 90-107, 476-485**

**Content**:
- Maven Local Repository section
- Docker Infrastructure section (basics)
- PowerShell Environment (detailed commands)
- Development workflow setup

**Why separate**: Setup is needed once, then rarely referenced.

**Link from main**:
```markdown
## Setup & Installation

**First Time Setup**: See [Setup Guide](infrastructure/SETUP_AND_INSTALLATION.md)

**Quick Start**:
1. Start infrastructure: `./misc/dev-support/docker/infrastructure/scripts/95_start.sh`
2. Maven local repo: `.m2-local/` (workspace-specific)
3. Build: Use `metasfresh-compile-and-test` skill
```

#### 1.4 `workflows/GIT_AND_CI.md` (100 lines)
**Extract from main CLAUDE.md lines 340-365, 929-941**

**Content**:
- Git Workflow section (complete)
- Branch protection
- Committing changes
- Creating pull requests
- CI/CD Pipeline overview

**Why separate**: Git workflow is procedural, needed occasionally.

**Link from main**:
```markdown
## Git Workflow

**Main branch**: `new_dawn_uat` (branch protection enabled)

**Always use feature branches**. See: [Git & CI/CD Workflows](workflows/GIT_AND_CI.md)
```

#### 1.5 `integrations/PLAYWRIGHT_MCP.md` (250 lines)
**Extract from main CLAUDE.md lines 536-911**

**Content**:
- Playwright MCP Integration section (complete)
- Available Skills and Commands
- Prerequisites
- Key MCP Tools and Best Practices
- Workflow for Creating Tests
- Code Patterns and Conventions
- Debugging with MCP

**Why separate**: MCP integration is specialized tool guidance.

**Link from main**:
```markdown
## Playwright E2E Testing

For creating E2E tests using Playwright MCP integration:

**See**:
- [Playwright MCP Guide](integrations/PLAYWRIGHT_MCP.md) - Using MCP for test creation
- [E2E Testing Guide](e2e/frontend-webui/CLAUDE.md) - Writing and debugging tests
```

#### 1.6 `syncing/CLAUDE_CONFIG_SYNC.md` (150 lines)
**Extract from main CLAUDE.md lines 250-338**

**Content**:
- Syncing Claude Configuration Files section (complete)
- Central repository (mf15-ai-dev-support)
- Finding the location
- Syncing/installing new files
- Committing changes to existing files

**Why separate**: Configuration sync is infrastructure/meta work.

**Link from main**:
```markdown
## Syncing Claude Configuration

CLAUDE.md and related files are symlinked from `mf15-ai-dev-support`.

**See**: [Claude Config Sync Guide](syncing/CLAUDE_CONFIG_SYNC.md)
```

### 1.7 New Main CLAUDE.md Structure (150 lines)

**Keep in main file**:
1. About metasfresh (lines 1-11)
2. ⚠️ CRITICAL protocol (lines 13-36) - ESSENTIAL
3. Quick Start for New Developers (NEW - 20 lines)
4. Project Structure (NEW - 30 lines, visual tree)
5. Windows PowerShell Environment (lines 38-89) - Keep summary, link to detailed guide
6. Subfolder Documentation (lines 109-169) - Navigation hub
7. Skills (lines 171-248) - Brief descriptions + links
8. IntelliJ MCP Integration (lines 366-384) - Keep brief
9. Documentation Resources (lines 386-394) - Context7 guidance
10. Tips for Claude Code (lines 912-965) - Keep essentials

**Total**: ~145 lines

---

## Phase 2: Frontend Refactoring (Week 2-3) - HIGH PRIORITY

### Goal
Split frontend/CLAUDE.md from 1,178 → ~300 lines main + 3 context files

### Files to Create

#### 2.1 `frontend/REDUX_PATTERNS.md` (250 lines)
**Extract from frontend/CLAUDE.md**

**Content**:
- Redux Action Pattern
- Redux Reducer Pattern (immutability-helper)
- Selector Pattern (Reselect)
- State Management best practices

#### 2.2 `frontend/COMPONENTS_AND_WIDGETS.md` (200 lines)
**Extract from frontend/CLAUDE.md**

**Content**:
- Component Patterns (Class vs Functional)
- Container Pattern
- Widget Pattern
- Dictionary (Component Terminology)

#### 2.3 `frontend/API_AND_WEBSOCKET.md` (150 lines)
**Extract from frontend/CLAUDE.md**

**Content**:
- API Integration Pattern
- WebSocket (STOMP) Pattern
- HTTP & Real-time Communication

#### 2.4 `frontend/TROUBLESHOOTING.md` (200 lines)
**Extract from frontend/CLAUDE.md**

**Content**:
- Common Issues & Troubleshooting
- Development Server Won't Start
- Hot Reload Not Working
- API Calls Failing (CORS)
- Build Fails
- Tests Failing
- WebSocket Connection Issues

**New frontend/CLAUDE.md** (300 lines):
- Overview
- Quick Start (setup, run, test, lint)
- Project Structure
- Technology Stack (summary)
- Development Workflow
- Linting (CRITICAL section - keep prominent)
- Navigation to other docs

---

## Phase 3: E2E Refactoring (Week 4) - MEDIUM PRIORITY

### Goal
Split e2e/frontend-webui/CLAUDE.md from 858 → ~250 lines main + supporting docs

### Files to Create

#### 3.1 `e2e/frontend-webui/SELECTOR_PATTERNS.md` (200 lines)
**Extract from e2e/CLAUDE.md**

**Content**:
- Language-Independent Testing principles
- Selector Priority hierarchy
- When to Add data-testid
- Using data-cy on Grid Cells (NEW - today's learning)
- Components That Should Have data-testid

#### 3.2 `e2e/frontend-webui/TIMING_AND_WAITS.md` (150 lines)
**Extract from e2e/CLAUDE.md**

**Content**:
- Dynamic Form Loading pattern
- Two-Step Wait Pattern
- Timeout Configuration
- Common Pitfalls (timing-related)

#### 3.3 `e2e/frontend-webui/WEBAPI_VALIDATION.md` (150 lines)
**Extract from e2e/CLAUDE.md**

**Content**:
- WebAPI Validation section (complete)
- Parent Records Must Be Saved First
- The Problem / The Solution
- Workflow Pattern
- Auto-Fill Behavior
- Key Utilities
- Debugging Tips

#### 3.4 `e2e/frontend-webui/LESSONS_LEARNED.md` (200 lines)
**Extract from e2e/CLAUDE.md**

**Content**:
- Lessons Learned (Ranked by Confidence)
- HIGH CONFIDENCE lessons
- MEDIUM CONFIDENCE lessons
- LOW CONFIDENCE lessons
- Add new lesson: "Using data-cy on grid cells"

**New e2e/frontend-webui/CLAUDE.md** (250 lines):
- Overview
- ⚠️ CRITICAL: Always Test Before Completing
- Quick Start (run tests, debug)
- Multi-Language Test Pattern
- Backend API Integration
- Page Object Model Pattern
- Navigation to detailed patterns
- Next Steps

---

## Implementation Steps

### Week 1: Phase 1 - Core Split

**Day 1-2: Create new files**
1. Create directory structure:
   ```
   backend/
   infrastructure/
   workflows/
   integrations/
   syncing/
   ```

2. Extract content to new files (use exact line ranges above)

3. Update main CLAUDE.md with links

**Day 3: Add Native Format Sections**
1. Add Project Structure diagram
2. Add Quick Start for New Developers
3. Add Architecture Overview (brief)

**Day 4-5: Test & Refine**
1. Verify all links work
2. Test with actual Claude Code session
3. Check symlink in mf15-ai-dev-support updates correctly

### Week 2-3: Phase 2 - Frontend Split

**Day 1-3: Extract frontend sections**
1. Create frontend subdirectories
2. Move sections to context files
3. Update main frontend/CLAUDE.md

**Day 4-5: Test & Refine**
1. Verify frontend developers can find info quickly
2. Test linting workflow (critical path)

### Week 4: Phase 3 - E2E Split

**Day 1-3: Extract e2e sections**
1. Create pattern-specific files
2. Add today's learning (data-cy grid cells)
3. Update main e2e/CLAUDE.md

**Day 4-5: Final Testing**
1. Run through complete E2E workflow
2. Verify multi-language test guidance is clear
3. Document the refactoring in release notes

---

## Validation Checklist

After each phase:

- [ ] Main file is under target line count
- [ ] All internal links work correctly
- [ ] Cross-references between files are accurate
- [ ] Quick start guides are prominent
- [ ] Critical sections (linting, test protocol, etc.) are easily found
- [ ] Symlinks in mf15-ai-dev-support are updated
- [ ] Changes are committed with clear messages
- [ ] Team is notified of new structure

---

## Rollback Plan

If issues arise:

1. **Keep old files**: Rename to `CLAUDE.md.backup` before splitting
2. **Test incrementally**: One phase at a time, validate before proceeding
3. **Symlink updates**: Use `migrate-new-files.ps1` to sync to central repo
4. **Communication**: Announce changes in team channel with before/after navigation guide

---

## Success Metrics

**Quantitative**:
- Main CLAUDE.md: <150 lines ✓
- Frontend CLAUDE.md: <300 lines ✓
- E2E CLAUDE.md: <250 lines ✓
- Quality score: >85/100 ✓

**Qualitative**:
- Developers find info faster (measure via informal feedback)
- Reduced "where do I find X?" questions
- Claude Code provides more accurate guidance (fewer hallucinations due to clearer context)

---

## Risk Mitigation

**Risk**: Breaking existing workflows
**Mitigation**: Keep old content, just reorganize. Add redirects in main file.

**Risk**: Links become outdated
**Mitigation**: Use relative links, test all links before committing.

**Risk**: Loss of context
**Mitigation**: Maintain cross-references, add "See Also" sections.

**Risk**: Team confusion
**Mitigation**: Announce changes, provide migration guide, keep main file as navigation hub.

---

## Next Actions

1. **Get approval** for this refactoring plan
2. **Schedule Week 1** work (Phase 1)
3. **Create backup** of current CLAUDE.md files
4. **Begin extraction** following the detailed steps above

**Estimated Effort**:
- Phase 1: 8-10 hours
- Phase 2: 6-8 hours
- Phase 3: 4-6 hours
- **Total**: ~20 hours over 4 weeks

**Ready to proceed?**
