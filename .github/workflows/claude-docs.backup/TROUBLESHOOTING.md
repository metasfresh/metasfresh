# CI/CD Troubleshooting

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Common issues and debugging**

## Common Issues

### Maven Dependency Resolution Fails

**Symptom**: `401 Unauthorized` errors during Maven build

**Fix**:
- Verify `METASFRESH_PACKAGES_READ_TOKEN` secret is set
- Check PAT has `read:packages` scope
- Ensure `envsubst` in prepare-settings step works

### Docker Push Fails

**Symptom**: Timeout or network errors during `docker push`

**Fix**:
- Check Docker Hub rate limits
- Verify `DOCKERHUB_METASFRESH_RW_TOKEN` is valid
- Increase `RETRY_ATTEMPTS` or `RETRY_TIMEOUT` variables

### Cucumber Tests Timeout

**Symptom**: Job exceeds 120-minute timeout

**Fix**:
- Check if specific tests are hanging
- Review test distribution across profiles
- Consider adding tests to `flaky` category
- Increase timeout in cucumber-run job

### Git Plugin Fails

**Symptom**: `git-commit-id-plugin` errors during Maven build

**Solution**: Always use `-Dmaven.gitcommitid.skip=true` flag (already done)

### Test Results Missing

**Symptom**: Testspace shows incomplete results

**Fix**:
- Check test containers produce expected XML files
- Verify `find` commands in push-results steps
- Ensure test images expose results at expected paths

## Required Secrets

| Secret | Purpose |
|--------|---------|
| `DOCKERHUB_METASFRESH_RW_TOKEN` | Write access to Docker Hub |
| `METASFRESH_PACKAGES_READ_TOKEN` | Read GitHub Packages |
| `TESTSPACE_TOKEN` | Upload test results |
| `TEST_SMTP_*` | SMTP for email tests |
| `CICD_TRIGGER_DISPATCH` | Trigger deployment workflows |

## Required Variables

| Variable | Purpose |
|----------|---------|
| `RETRY_ATTEMPTS` | Number of Docker push retries (default: 3) |
| `RETRY_DELAY` | Delay between retries in seconds (default: 5) |
| `RETRY_TIMEOUT` | Timeout per retry in minutes (default: 10) |
| `CICD_BRANCH_MAP` | Branches that trigger deployment |
| `CICD_DISPATCH_CONFIG` | Deployment workflow configuration |

## Monitoring and Debugging

### Test Results
- **Testspace**: https://metasfresh.testspace.com/
- Organized by branch and test type

### GitHub Actions
- **Workflow runs**: https://github.com/metasfresh/metasfresh/actions
- Check job summaries for image tags and test counts
- Download artifacts for failed runs

### Docker Images
- **Docker Hub**: https://hub.docker.com/u/metasfresh
- Search by tag pattern: `{version}-{branch}.{number}`

### Health Check Debugging
```bash
docker inspect --format "{{json .State.Health }}" container-name | jq
```

## Tips for Claude Code

### Monitoring CI Builds (IMPORTANT)

**Use a Task agent to monitor long-running builds:**
```
Use Task tool with subagent_type='general-purpose' to:
1. Monitor build {RUN_ID} until completion
2. Check for failures in test jobs
3. Return a summary of results
```

**Why:** Builds take 45-60 minutes. Polling in main conversation consumes context rapidly.

### When Fixing Build Issues
1. Check both `exit-code` AND `mvn.exit-code` files
2. Look for errors in logs (last 1000 lines)
3. Verify Docker BuildKit is enabled
4. Check secrets are properly mounted
5. Ensure metadata files are created correctly

### When Debugging Tag Issues
1. Check init job outputs (tag-floating, tag-fixed)
2. Verify version.info is readable
3. Ensure sanitize step removes special chars
4. Check that REFNAME build-arg is passed correctly
