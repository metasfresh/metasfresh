import { expect, test } from '@playwright/test';

/**
 * Print Options — @SQL= default value resolution E2E test.
 *
 * gh#29279: Verifies that AD_Process_Para.DefaultValue with @SQL= expressions
 * are correctly resolved by DocumentPrintOptionDescriptorsRepository.
 *
 * The migration script 5797990 sets the Lieferschein (Jasper) PRINTER_OPTS_IsPrintLogo
 * default to: @SQL=SELECT CASE WHEN @#AD_Client_ID@ > 0 THEN 'Y' ELSE 'N' END
 *
 * This test verifies that the printingOptions endpoint returns value=true for
 * IsPrintLogo, proving the @SQL= expression was resolved (not passed as raw string).
 *
 * Window 169 = Shipment (M_InOut)
 * AD_Process_Para_ID 541909 = Lieferschein (Jasper) / PRINTER_OPTS_IsPrintLogo
 */

const SHIPMENT_WINDOW_ID = '169';
// In CI: WEBAPI_BASE_URL=http://webui:80/rest/api (via nginx proxy)
// Locally: http://localhost:8080/rest/api (direct to webapi)
const WEBAPI_URL = process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api';

test.describe('Print Options @SQL= default resolution', () => {
  test.setTimeout(60000);

  test('Shipment printingOptions resolves @SQL= IsPrintLogo default to true', async ({ request }) => {
    // Step 1: Authenticate
    const loginResp = await request.post(`${WEBAPI_URL}/login/authenticate`, {
      data: { type: 'password', username: 'metasfresh', password: 'metasfresh' },
    });
    expect(loginResp.ok(), `Login failed with ${loginResp.status()}`).toBeTruthy();
    const loginData = await loginResp.json();

    const role = loginData.roles?.find((r) => r.caption?.includes('WebUI')) || loginData.roles?.[0];
    expect(role, 'No role found in login response').toBeTruthy();

    const completeResp = await request.post(`${WEBAPI_URL}/login/loginComplete`, {
      data: { roleId: role.roleId, tenantId: role.tenantId, orgId: role.orgId },
    });
    expect(completeResp.ok(), `loginComplete failed with ${completeResp.status()}`).toBeTruthy();

    // Step 2: Find a shipment document via view
    const viewCreateResp = await request.post(
      `${WEBAPI_URL}/documentView/${SHIPMENT_WINDOW_ID}`,
      { data: { windowId: SHIPMENT_WINDOW_ID, viewType: 'grid', firstRow: 0, pageLength: 1 } }
    );
    expect(viewCreateResp.ok(), `Create view failed with ${viewCreateResp.status()}`).toBeTruthy();
    const viewData = await viewCreateResp.json();

    const rowsResp = await request.get(
      `${WEBAPI_URL}/documentView/${SHIPMENT_WINDOW_ID}/${viewData.viewId}?firstRow=0&pageLength=1`
    );
    expect(rowsResp.ok(), `Get rows failed with ${rowsResp.status()}`).toBeTruthy();
    const rowsData = await rowsResp.json();

    const documentId = rowsData.result?.[0]?.id;
    expect(documentId, 'Need at least one shipment record in the DB').toBeTruthy();

    // Step 3: Call printingOptions endpoint — THE CRITICAL TEST
    const printResp = await request.get(
      `${WEBAPI_URL}/window/${SHIPMENT_WINDOW_ID}/${documentId}/printingOptions`
    );
    expect(printResp.ok(), `printingOptions returned ${printResp.status()}`).toBeTruthy();
    const printOptions = await printResp.json();

    // Step 4: Verify IsPrintLogo resolved to true from @SQL= expression
    const logoOption = printOptions.options?.find(
      (opt) => opt.internalName === 'PRINTER_OPTS_IsPrintLogo'
    );
    expect(logoOption, 'PRINTER_OPTS_IsPrintLogo option must exist').toBeTruthy();
    expect(logoOption.value, 'IsPrintLogo must be true (@SQL= default resolved correctly)').toBe(true);
    expect(logoOption.debugSourceName).toContain('AD_Process defaults');
  });
});
