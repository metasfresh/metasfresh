import Metasfresh from './page';
import config from '../config';

class Inventory extends Metasfresh {
  constructor() {
    super();

    this.windowId = 168;
    this.docTypeDisposal = 540948;
    this.docTypeInventoryWithMultipleHUs = 540971;
    this.docTypeInventoryWithSingleHU = 1000023;
    this.inventoryLineTabId = 'AD_Tab-256';
  }

  // can this be deleted? if it's past 2019.11 then yes!
  // /**
  //  * Gets the inventory data and snapshots it, ignoring properties that are not the invariant between different tests.
  //  *
  //  * @param inventoryRecord the record to snapshot. Its id can by accessed via inventoryRecord.documentId
  //  * @param labelPrefix prefix for the snapshot label to use
  //  */
  // toMatchSnapshots(inventoryRecord, labelPrefix) {
  //   cy.log(`InventoryId=${inventoryRecord.documentId}`);
  //   cy.request('GET', `${config.API_URL}/window/${inventory.windowId}/${inventoryRecord.documentId}`).then(response => {
  //     expect(response.body, 'inventoryJson - length').to.have.lengthOf(1);
  //     const inventoryJson = response.body[0];
  //
  //     // remove the fields that are different on each test run from the JSON; the result is invariant between test runs
  //     inventoryJson.id = 'REPLACED_WITH_CONST';
  //     inventoryJson.websocketEndpoint = 'REPLACED_WITH_CONST';
  //     inventoryJson.fieldsByName.DocumentNo.value = 'REPLACED_WITH_CONST';
  //     inventoryJson.fieldsByName.V$DocumentSummary = 'REPLACED_WITH_CONST';
  //     inventoryJson.fieldsByName.ID.value = 'REPLACED_WITH_CONST';
  //     inventoryJson.fieldsByName.MovementDate.value = 'REPLACED_WITH_CONST';
  //     inventoryJson.fieldsByName.Posted.displayed = 'WE_DONT_CARE_IN_THIS_SPEC';
  //     cy.wrap(inventoryJson).toMatchSnapshot(`${labelPrefix}_header`);
  //   });
  //
  //   cy.request(
  //     'GET',
  //     `${config.API_URL}/window/${inventory.windowId}/${inventoryRecord.documentId}/${inventory.inventoryLineTabId}`
  //   ).then(response => {
  //     const inventoryLinesJson = response.body;
  //
  //     inventoryLinesJson.forEach(function(inventoryLineJson) {
  //       inventoryLineJson.id = 'REPLACED_WITH_CONST';
  //       inventoryLineJson.rowId = 'REPLACED_WITH_CONST';
  //       inventoryLineJson.websocketEndpoint = 'REPLACED_WITH_CONST';
  //       inventoryLineJson.fieldsByName.ID.value = 'REPLACED_WITH_CONST';
  //       inventoryLineJson.fieldsByName.M_HU_ID.value = 'REPLACED_WITH_CONST';
  //       inventoryLineJson.fieldsByName.M_Product_ID.value = 'REPLACED_WITH_CONST';
  //     });
  //
  //     cy.wrap(inventoryLinesJson).toMatchSnapshot(`${labelPrefix}_lives`);
  //   });
  // }
}

export const inventory = new Inventory();
