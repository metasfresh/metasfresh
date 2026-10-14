// WebAPI node (not the app-server), e.g. for its /cache/reset endpoints.
// Same module and name as on new_dawn_uat, so specs importing it work on both.
export const WEBAPI_BASE_URL = process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api';
