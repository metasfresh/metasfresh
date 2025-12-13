/**
 * Allure Report Integration Helpers
 *
 * Provides utilities for enriching Playwright test reports with:
 * - Epic/Feature/Story hierarchy
 * - Tabular data attachments
 * - PDF and screenshot attachments
 * - Links to GitHub issues
 * - Test parameters and descriptions
 *
 * Usage:
 *   import { AllureHelpers } from '../utils/AllureHelpers';
 *
 *   test('My test', async ({ page }) => {
 *     // Single feature (looked up from google-sheets-sync skill)
 *     await AllureHelpers.setFeature({
 *       id: 'F00100',
 *       name: 'Sales Order',
 *       epicId: 'E0100',
 *       epicName: 'Sales'
 *     });
 *
 *     // Multiple features (for tests spanning multiple areas)
 *     await AllureHelpers.setFeatures([
 *       { id: 'F00600', name: 'Purchase Order', epicId: 'E0140', epicName: 'Purchasing' },
 *       { id: 'F65010', name: 'Material Receipt Candidates', epicId: 'E0150', epicName: 'Material Receipt' }
 *     ]);
 *
 *     await AllureHelpers.setStory('Create sales order');
 *     await AllureHelpers.attachTable('Order Lines', [...], ['Product', 'Qty']);
 *   });
 */

const allure = require('allure-js-commons');
const { ContentType } = require('allure-js-commons');

// PDF content type is not in allure-js-commons ContentType enum
// Define it as a custom constant
const PDF_CONTENT_TYPE = 'application/pdf';

/**
 * Allure integration helpers for metasfresh E2E tests.
 */
const AllureHelpers = {
  /**
   * Set feature for the current test with inline metadata.
   * Automatically sets the epic as well if epicId and epicName are provided.
   * Can be called multiple times to add multiple features.
   *
   * @param {Object|string} featureOrId - Feature object or ID string
   * @param {string} featureOrId.id - Feature ID (e.g., 'F00100')
   * @param {string} featureOrId.name - Feature name (e.g., 'Sales Order')
   * @param {string} [featureOrId.epicId] - Epic ID (e.g., 'E0100')
   * @param {string} [featureOrId.epicName] - Epic name (e.g., 'Sales')
   * @example
   *   // Recommended: pass full metadata (from google-sheets-sync skill)
   *   await AllureHelpers.setFeature({
   *     id: 'F00100',
   *     name: 'Sales Order',
   *     epicId: 'E0100',
   *     epicName: 'Sales'
   *   });
   *
   *   // Minimal: just ID and name
   *   await AllureHelpers.setFeature({ id: 'F00100', name: 'Sales Order' });
   *
   *   // Legacy: string ID (will show warning)
   *   await AllureHelpers.setFeature('F00100');
   */
  async setFeature(featureOrId) {
    // Handle legacy string usage
    if (typeof featureOrId === 'string') {
      console.warn(`setFeature('${featureOrId}'): Pass feature object for full metadata`);
      await allure.feature(featureOrId);
      return;
    }

    const { id, name, epicId, epicName } = featureOrId;

    // Set epic if provided
    if (epicId && epicName) {
      await allure.epic(`${epicId}: ${epicName}`);
    }

    // Set feature
    await allure.feature(`${id}: ${name}`);
  },

  /**
   * Set multiple features for the current test.
   * Useful for tests that span multiple features/epics.
   *
   * @param {Array<Object>} features - Array of feature objects
   * @example
   *   await AllureHelpers.setFeatures([
   *     { id: 'F00600', name: 'Purchase Order', epicId: 'E0140', epicName: 'Purchasing' },
   *     { id: 'F65010', name: 'Material Receipt Candidates', epicId: 'E0150', epicName: 'Material Receipt' }
   *   ]);
   */
  async setFeatures(features) {
    for (const feature of features) {
      await this.setFeature(feature);
    }
  },

  /**
   * Set epic manually with inline metadata.
   *
   * @param {Object|string} epicOrName - Epic object or name string
   * @param {string} epicOrName.id - Epic ID (e.g., 'E0100')
   * @param {string} epicOrName.name - Epic name (e.g., 'Sales')
   * @example
   *   // Recommended: pass full metadata
   *   await AllureHelpers.setEpic({ id: 'E0100', name: 'Sales' });
   *
   *   // Legacy: string name
   *   await AllureHelpers.setEpic('Sales');
   */
  async setEpic(epicOrName) {
    // Handle legacy string usage
    if (typeof epicOrName === 'string') {
      await allure.epic(epicOrName);
      return;
    }

    const { id, name } = epicOrName;
    await allure.epic(`${id}: ${name}`);
  },

  /**
   * Set story for the current test.
   *
   * @param {string} storyName - Story description
   * @example
   *   await AllureHelpers.setStory('Create sales order with batch entry');
   */
  async setStory(storyName) {
    await allure.story(storyName);
  },

  /**
   * Link to a GitHub issue.
   *
   * @param {string} issueNumber - GitHub issue number
   * @param {string} [description] - Optional description
   * @example
   *   await AllureHelpers.linkIssue('1234', 'Batch entry timing issue');
   */
  async linkIssue(issueNumber, description = '') {
    const url = `https://github.com/metasfresh/metasfresh/issues/${issueNumber}`;
    await allure.issue(url, description || `Issue #${issueNumber}`);
  },

  /**
   * Attach tabular data (order lines, invoice lines, etc.) as HTML table.
   *
   * @param {string} name - Attachment name (e.g., 'Order Lines')
   * @param {Array<Object>} data - Array of objects to display as table
   * @param {Array<string>} columns - Column headers to include
   * @example
   *   await AllureHelpers.attachTable('Order Lines',
   *     [{ Product: 'PROD-001', Quantity: '10', UOM: 'PCE' }],
   *     ['Product', 'Quantity', 'UOM']
   *   );
   */
  async attachTable(name, data, columns) {
    const html = this._generateTableHtml(data, columns);
    await allure.attachment(name, html, ContentType.HTML);
  },

  /**
   * Attach PDF file with optional metadata.
   *
   * @param {string} name - Attachment name (e.g., 'Sales Order PDF')
   * @param {Buffer|string} content - PDF content (Buffer) or file path (string)
   * @param {Object} [metadata] - Optional metadata to attach as JSON
   * @example
   *   const pdfBuffer = await download.path();
   *   await AllureHelpers.attachPdf('Sales Order PDF', pdfBuffer, { documentNo: 'SO-001' });
   */
  async attachPdf(name, content, metadata = {}) {
    // If content is a path, read the file
    let pdfBuffer = content;
    if (typeof content === 'string') {
      const fs = require('fs');
      pdfBuffer = fs.readFileSync(content);
    }

    await allure.attachment(name, pdfBuffer, PDF_CONTENT_TYPE);

    // Attach metadata as JSON if provided
    if (Object.keys(metadata).length > 0) {
      await allure.attachment(
        `${name} - Metadata`,
        JSON.stringify(metadata, null, 2),
        ContentType.JSON
      );
    }
  },

  /**
   * Attach screenshot from Playwright page.
   *
   * @param {Page} page - Playwright page object
   * @param {string} name - Screenshot name
   * @example
   *   await AllureHelpers.attachScreenshot(page, 'Shipment Schedule View');
   */
  async attachScreenshot(page, name) {
    const screenshot = await page.screenshot();
    await allure.attachment(name, screenshot, ContentType.PNG);
  },

  /**
   * Add test parameter for display in report.
   *
   * @param {string} name - Parameter name
   * @param {string} value - Parameter value
   * @example
   *   await AllureHelpers.addParameter('Language', 'en_US');
   *   await AllureHelpers.addParameter('Document No', 'SO-001');
   */
  async addParameter(name, value) {
    await allure.parameter(name, String(value));
  },

  /**
   * Set test severity level.
   *
   * @param {'blocker'|'critical'|'normal'|'minor'|'trivial'} severity
   * @example
   *   await AllureHelpers.setSeverity('critical');
   */
  async setSeverity(severity) {
    await allure.severity(severity);
  },

  /**
   * Set test description (supports Markdown).
   *
   * @param {string} description - Test description in Markdown
   * @example
   *   await AllureHelpers.setDescription(`
   *     ## Test Scenario
   *     This test validates the sales order to shipment schedule workflow.
   *   `);
   */
  async setDescription(description) {
    await allure.description(description);
  },

  /**
   * Set test description in HTML format.
   *
   * @param {string} html - HTML description
   */
  async setDescriptionHtml(html) {
    await allure.descriptionHtml(html);
  },

  /**
   * Add custom label to the test.
   *
   * @param {string} name - Label name
   * @param {string} value - Label value
   */
  async addLabel(name, value) {
    await allure.label(name, value);
  },

  /**
   * Add multiple tags to the test.
   *
   * @param {...string} tags - Tags to add
   * @example
   *   await AllureHelpers.addTags('smoke', 'sales', 'pdf');
   */
  async addTags(...tags) {
    await allure.tags(...tags);
  },

  /**
   * Attach test data summary (masterdata created for the test).
   *
   * @param {Object} masterdata - Masterdata object from Backend.createMasterdata()
   * @example
   *   const masterdata = await Backend.createMasterdata({...});
   *   await AllureHelpers.attachTestData(masterdata);
   */
  async attachTestData(masterdata) {
    const summary = [];

    // User info
    if (masterdata.login?.user) {
      summary.push({
        Type: 'User',
        Code: masterdata.login.user.login || masterdata.login.user.username,
        Name: `${masterdata.login.user.firstname} ${masterdata.login.user.lastname}`,
        Details: `Language: ${masterdata.login.user.language}`,
      });
    }

    // Business Partners
    if (masterdata.bpartners) {
      Object.entries(masterdata.bpartners).forEach(([key, bp]) => {
        summary.push({
          Type: 'Business Partner',
          Code: bp.bpartnerCode,
          Name: bp.bpartnerName || key,
          Details: bp.isCustomer ? 'Customer' : bp.isVendor ? 'Vendor' : '',
        });
      });
    }

    // Products
    if (masterdata.products) {
      Object.entries(masterdata.products).forEach(([key, prod]) => {
        summary.push({
          Type: 'Product',
          Code: prod.productCode,
          Name: prod.productName || key,
          Details: prod.type || '',
        });
      });
    }

    if (summary.length > 0) {
      await this.attachTable('Test Data', summary, ['Type', 'Code', 'Name', 'Details']);
    }
  },

  /**
   * Generate HTML table from data.
   * @private
   */
  _generateTableHtml(data, columns) {
    if (!data || data.length === 0) {
      return '<p>No data</p>';
    }

    const headerCells = columns
      .map(col => `<th style="padding:8px 12px;border:1px solid #ddd;background:#f5f5f5;text-align:left;font-weight:600;">${col}</th>`)
      .join('');

    const rows = data.map((row, rowIndex) => {
      const cells = columns.map(col => {
        const value = row[col] !== undefined ? row[col] : '';
        return `<td style="padding:8px 12px;border:1px solid #ddd;background:${rowIndex % 2 === 0 ? '#fff' : '#fafafa'};">${value}</td>`;
      }).join('');
      return `<tr>${cells}</tr>`;
    }).join('');

    return `
      <table style="border-collapse:collapse;width:100%;font-family:Arial,sans-serif;font-size:13px;margin:10px 0;">
        <thead><tr>${headerCells}</tr></thead>
        <tbody>${rows}</tbody>
      </table>
    `;
  },
};

module.exports = { AllureHelpers };
