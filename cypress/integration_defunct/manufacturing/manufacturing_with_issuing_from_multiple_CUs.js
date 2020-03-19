/*
 * #%L
 * metasfresh-e2e
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import { Product, ProductCategory } from '../../support/utils/product';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { BillOfMaterial, BillOfMaterialLine } from '../../support/utils/billOfMaterial';
import { Builder } from '../../support/utils/builder';
import { ColumnAndValue } from '../../support/commands/navigation';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';

// testdata
let categoryName1;
let categoryName2;
let productComponentName1;
let productComponentName2;
let productComponentName3;
let finishedGoodName;
let productComponentQty1;
let productComponentQty2;
let productComponentQty3;

// HU
let huQty;
let locatorId;

// manufacturing order
let manufacturingResource;
let manufacturingWorkflow;
let stdWarehouse;
let manufacturingQtyEntered;
let manufacturingDateOrdered;
let manufacturingDatePromised;

// static
const expectedManufacturingPriorityRule = 'Medium';
const eachUOM = 'Each';
const bomIssueMethod = 'Issue only for what was received';
const expectedManufacturingTargetDocType = 'Produktionsauftrag';
const packingStatusActive = 'Active';
const packingStatusDestroyed = 'Destroyed';
const packingStatusPlanning = 'Planning';
const packingStatusIssued = 'Issued';
const expectNoPackingItem = 'No Packing Item';

// columns
const manufacturingOrderComponentsProductColumn = 'M_Product_ID';
const productColumn = 'product';
const typeColumn = 'type';
const qtyPlanColumn = 'qtyPlan';
const qtyColumn = 'qty';
const pickingCodeColumn = 'code';
const pickingStatusColumn = 'huStatus';
const huSelectionCodeColumn = 'Value';
const qtyCuColumn = 'QtyCU';

// test
let huValue1;
let huValue2;
let huValue3;

describe('Create test data', function() {
  it('Read the fixture', function() {
    cy.fixture('manufacturing/manufacturing_with_issuing_from_multiple_CUs.json').then(f => {
      // testdata
      categoryName1 = appendHumanReadableNow(f['categoryName1']);
      categoryName2 = appendHumanReadableNow(f['categoryName2']);
      productComponentName1 = appendHumanReadableNow(f['productComponentName1']);
      productComponentName2 = appendHumanReadableNow(f['productComponentName2']);
      productComponentName3 = appendHumanReadableNow(f['productComponentName3']);
      finishedGoodName = appendHumanReadableNow(f['finishedGoodName']);
      productComponentQty1 = f['productComponentQty1'];
      productComponentQty2 = f['productComponentQty2'];
      productComponentQty3 = f['productComponentQty3'];

      // HU
      huQty = f['huQty'];
      locatorId = f['locatorId'];

      // manufacturing order
      manufacturingResource = f['manufacturingResource'];
      manufacturingWorkflow = f['manufacturingWorkflow'];
      stdWarehouse = f['stdWarehouse'];
      manufacturingQtyEntered = f['manufacturingQtyEntered'];
      manufacturingDateOrdered = f['manufacturingDateOrdered'];
      manufacturingDatePromised = f['manufacturingDatePromised'];
    });
  });

  it('Create product components', function() {
    // first component
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(categoryName1)
        .apply();
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productComponentName1)
        .setProductCategory(categoryName1)
        .apply();
    });

    // second component
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(categoryName2)
        .apply();
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productComponentName2)
        .setProductCategory(categoryName2)
        .apply();
    });

    // third component
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productComponentName3)
        .setProductCategory(categoryName2)
        .apply();
    });
  });

  it('Create finished good', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(finishedGoodName)
        .setProductCategory(categoryName1)
        .apply();
    });
  });

  it('Create a BOM for the product', function() {
    cy.fixture('product/bill_of_material.json').then(billMaterialJson => {
      Object.assign(new BillOfMaterial(), billMaterialJson)
        .setProduct(finishedGoodName)
        .setIsVerified(true)
        // eslint-disable-next-line prettier/prettier
        .addLine(new BillOfMaterialLine().setProduct(productComponentName1).setQuantity(productComponentQty1).setIssueMethod(bomIssueMethod))
        // eslint-disable-next-line prettier/prettier
        .addLine(new BillOfMaterialLine().setProduct(productComponentName2).setQuantity(productComponentQty2).setIssueMethod(bomIssueMethod))
        // eslint-disable-next-line prettier/prettier
        .addLine(new BillOfMaterialLine().setProduct(productComponentName3).setQuantity(productComponentQty3).setIssueMethod(bomIssueMethod))
        .apply();
    });
  });

  it('Create 3 HUs', function() {
    Builder.createHUWithStock(productComponentName1, huQty, locatorId).then(huVal => (huValue1 = huVal));
    Builder.createHUWithStock(productComponentName2, huQty, locatorId).then(huVal => (huValue2 = huVal));
    Builder.createHUWithStock(productComponentName3, huQty, locatorId).then(huVal => (huValue3 = huVal));
  });
});

describe('Case 1: Process the manufacturing order **after** the components are issued', function() {
  it('Create Manufacturing Order Doc', function() {
    cy.visitWindow('53009', 'NEW');

    cy.writeIntoLookupListField('M_Product_ID', finishedGoodName, finishedGoodName);
    cy.getStringFieldValue('PP_Product_BOM_ID').should('contain', finishedGoodName);
    cy.selectInListField('S_Resource_ID', manufacturingResource);
    cy.writeIntoLookupListField('AD_Workflow_ID', manufacturingWorkflow, manufacturingWorkflow);

    cy.getStringFieldValue('C_DocTypeTarget_ID').should('contain', expectedManufacturingTargetDocType);
    cy.getStringFieldValue('PriorityRule').should('contain', expectedManufacturingPriorityRule);
    cy.getStringFieldValue('QtyEntered').should('contain', manufacturingQtyEntered);
    cy.getStringFieldValue('C_UOM_ID').should('contain', eachUOM);

    cy.writeIntoStringField('DateOrdered', manufacturingDateOrdered, false, null, true);
    cy.writeIntoStringField('DatePromised', manufacturingDatePromised, false, null, true);
    cy.selectInListField('M_Warehouse_ID', stdWarehouse);

    // expect 3 lines in tab, 1 for each product inside the bom
    cy.selectTab('Window-53009-AD_Tab-53039');
    cy.expectNumberOfRows(3);
    cy.selectRowByColumnAndValue({ column: manufacturingOrderComponentsProductColumn, value: productComponentName1 });
    cy.selectRowByColumnAndValue({ column: manufacturingOrderComponentsProductColumn, value: productComponentName2 });
    cy.selectRowByColumnAndValue({ column: manufacturingOrderComponentsProductColumn, value: productComponentName3 });

    cy.completeDocument();
  });

  it('Run action "Issue/Receipt" and expect 4 rows', function() {
    cy.executeHeaderAction('WEBUI_PP_Order_IssueReceipt_Launcher');

    cy.expectNumberOfRows(4, true);
    cy.selectRowByColumnAndValue(createColumnAndValue(finishedGoodName, undefined, 'MP', manufacturingQtyEntered), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, undefined, 'CO', productComponentQty1), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, undefined, 'CO', productComponentQty2), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName3, undefined, 'CO', productComponentQty3), true);
  });

  it('Select first HU as source', function() {
    selectHuAsSource(productComponentName1, huValue1);
  });
  it('Select second HU as source', function() {
    selectHuAsSource(productComponentName2, huValue2);
  });
  it('Select third HU as source', function() {
    selectHuAsSource(productComponentName3, huValue3);
  });

  it('Receive the finished good', function() {
    cy.selectRowByColumnAndValue({ column: productColumn, value: finishedGoodName }, true);
    cy.executeQuickAction('WEBUI_PP_Order_Receipt', true);
    cy.getStringFieldValue('M_HU_PI_Item_Product_ID').should('contain', expectNoPackingItem);
    cy.writeIntoStringField('QtyCU', manufacturingQtyEntered, true);
    cy.pressStartButton();
    cy.selectRowByColumnAndValue(createColumnAndValue(finishedGoodName, undefined, 'CU', null, manufacturingQtyEntered, packingStatusPlanning), true);
  });

  it('Run action "Issue CUs from source HUs" when only component 1 is selected', function() {
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, null, 'CO'), true);
    cy.executeQuickAction('WEBUI_PP_Order_M_Source_HU_IssueCUQty', true);
    cy.getStringFieldValue('QtyCU').should('equals', productComponentQty1.toString(10));
    cy.pressStartButton();
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, undefined, 'CU', null, productComponentQty1, packingStatusIssued), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, undefined, 'CU', null, huQty - productComponentQty1, packingStatusActive), true);
  });

  it('Run action "Issue CUs from source HUs" when both component 2 and 3 are selected', function() {
    // multiple selection by pressing control and clicking
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, null, 'CO'), true)
      .get('body')
      .type('{control}', { release: false })
      .selectRowByColumnAndValue(createColumnAndValue(productComponentName3, null, 'CO'), true)
      .get('body')
      .type('{control}');

    cy.executeQuickAction('WEBUI_PP_Order_M_Source_HU_IssueCUQty', true);
    // i have the feeling this 500ms sleep may in certain cases not be enough, however currently i have no idea how "not to need" it.
    // note in case you want to wait for a `RewriteURL.DocumentLayout`: that's not gonna work, i have already tried that.
    // there are some cases where a new layout is not needed, even though something new appears onscreen. ¯\_(ツ)_/¯
    cy.pressStartButton(500);

    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, undefined, 'CU', null, productComponentQty2, packingStatusIssued), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, undefined, 'CU', null, huQty - productComponentQty2, packingStatusActive), true);

    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName3, undefined, 'CU', null, productComponentQty3, packingStatusIssued), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName3, undefined, 'CU', null, huQty - productComponentQty3, packingStatusActive), true);
  });

  it('Process the finished good', function() {
    cy.selectRowByColumnAndValue(createColumnAndValue(finishedGoodName, undefined, 'MP'), true);
    cy.executeQuickAction('WEBUI_PP_Order_ChangePlanningStatus_Complete', true, false);

    cy.selectRowByColumnAndValue(createColumnAndValue(finishedGoodName, undefined, 'CU', null, manufacturingQtyEntered, packingStatusActive), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, undefined, 'CU', null, productComponentQty1, packingStatusDestroyed), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, undefined, 'CU', null, productComponentQty2, packingStatusDestroyed), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName3, undefined, 'CU', null, productComponentQty3, packingStatusDestroyed), true);
  });

  it('Go to Handling Unit Editor and check expected qty of finished good', function() {
    cy.visitWindow('540189');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoLookupListField('M_Product_ID', finishedGoodName, finishedGoodName, false, false, null, true);
    applyFilters();

    cy.expectNumberOfRows(1);
    cy.selectRowByColumnAndValue({ column: qtyCuColumn, value: manufacturingQtyEntered });
  });
});

describe('Case 2: Process the manufacturing order **before** the components are issued', function() {
  it('Create Manufacturing Order Doc', function() {
    cy.visitWindow('53009', 'NEW');

    cy.writeIntoLookupListField('M_Product_ID', finishedGoodName, finishedGoodName);
    cy.getStringFieldValue('PP_Product_BOM_ID').should('contain', finishedGoodName);
    cy.selectInListField('S_Resource_ID', manufacturingResource);
    cy.writeIntoLookupListField('AD_Workflow_ID', manufacturingWorkflow, manufacturingWorkflow);

    cy.getStringFieldValue('C_DocTypeTarget_ID').should('contain', expectedManufacturingTargetDocType);
    cy.getStringFieldValue('PriorityRule').should('contain', expectedManufacturingPriorityRule);
    cy.getStringFieldValue('QtyEntered').should('contain', manufacturingQtyEntered);
    cy.getStringFieldValue('C_UOM_ID').should('contain', eachUOM);

    cy.writeIntoStringField('DateOrdered', manufacturingDateOrdered, false, null, true);
    cy.writeIntoStringField('DatePromised', manufacturingDatePromised, false, null, true);
    cy.selectInListField('M_Warehouse_ID', stdWarehouse);

    // expect 3 lines in tab, 1 for each product inside the bom
    cy.selectTab('Window-53009-AD_Tab-53039');
    cy.expectNumberOfRows(3);
    cy.selectRowByColumnAndValue({ column: manufacturingOrderComponentsProductColumn, value: productComponentName1 });
    cy.selectRowByColumnAndValue({ column: manufacturingOrderComponentsProductColumn, value: productComponentName2 });
    cy.selectRowByColumnAndValue({ column: manufacturingOrderComponentsProductColumn, value: productComponentName3 });

    cy.completeDocument();
  });

  it('Run action "Issue/Receipt" and expect 7 rows', function() {
    cy.executeHeaderAction('WEBUI_PP_Order_IssueReceipt_Launcher');

    cy.expectNumberOfRows(7, true);
    // 1 finished good
    cy.selectRowByColumnAndValue(createColumnAndValue(finishedGoodName, undefined, 'MP', manufacturingQtyEntered), true);
    // 3 components
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, undefined, 'CO', productComponentQty1), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, undefined, 'CO', productComponentQty2), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName3, undefined, 'CO', productComponentQty3), true);
    // 3 source HUs already marked as source in the previous Case
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, huValue1, 'CU', null, huQty - productComponentQty1, packingStatusActive), false, true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, huValue2, 'CU', null, huQty - productComponentQty2, packingStatusActive), false, true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName3, huValue3, 'CU', null, huQty - productComponentQty3, packingStatusActive), false, true);
  });

  it('Receive the finished good', function() {
    cy.selectRowByColumnAndValue({ column: productColumn, value: finishedGoodName }, true);
    cy.executeQuickAction('WEBUI_PP_Order_Receipt', true);
    cy.getStringFieldValue('M_HU_PI_Item_Product_ID').should('contain', expectNoPackingItem);
    cy.writeIntoStringField('QtyCU', manufacturingQtyEntered, true);
    cy.pressStartButton();
    cy.selectRowByColumnAndValue(createColumnAndValue(finishedGoodName, undefined, 'CU', null, manufacturingQtyEntered, packingStatusPlanning), true);
  });

  it('Process the finished good', function() {
    cy.selectRowByColumnAndValue(createColumnAndValue(finishedGoodName, undefined, 'MP'), true);
    cy.executeQuickAction('WEBUI_PP_Order_ChangePlanningStatus_Complete', true, false);

    cy.selectRowByColumnAndValue(createColumnAndValue(finishedGoodName, undefined, 'CU', null, manufacturingQtyEntered, packingStatusActive), true);
  });

  it('Run action "Issue CUs from source HUs" when both component 1 and 3 are selected', function() {
    cy.expectNumberOfRows(8, true);
    // multiple selection by pressing control and clicking
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, null, 'CO'), true)
      .get('body')
      .type('{control}', { release: false })
      .selectRowByColumnAndValue(createColumnAndValue(productComponentName3, null, 'CO'), true)
      .get('body')
      .type('{control}');

    cy.executeQuickAction('WEBUI_PP_Order_M_Source_HU_IssueCUQty', true);
    // i have the feeling this 500ms sleep may in certain cases not be enough, however currently i have no idea how "not to need" it.
    // note in case you want to wait for a `RewriteURL.DocumentLayout`: that's not gonna work, i have already tried that.
    // there are some cases where a new layout is not needed, even though something new appears onscreen. ¯\_(ツ)_/¯
    cy.pressStartButton(500);

    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, undefined, 'CU', null, productComponentQty1, packingStatusDestroyed), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName1, undefined, 'CU', null, huQty - 2 * productComponentQty1, packingStatusActive), true);

    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName3, undefined, 'CU', null, productComponentQty3, packingStatusDestroyed), true);
    // component 3 has qty=0 so it is deleted and it is missing from the dialog. because of this instead of 10 we only have 9 rows remaining
    cy.expectNumberOfRows(9, true);
  });

  it('Run action "Issue CUs from source HUs" when only component 2 is selected', function() {
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, null, 'CO'), true);
    cy.executeQuickAction('WEBUI_PP_Order_M_Source_HU_IssueCUQty', true);
    cy.getStringFieldValue('QtyCU').should('equals', productComponentQty2.toString(10));
    cy.pressStartButton();
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, undefined, 'CU', null, productComponentQty2, packingStatusDestroyed), true);
    cy.selectRowByColumnAndValue(createColumnAndValue(productComponentName2, undefined, 'CU', null, huQty - 2 * productComponentQty2, packingStatusActive), true);
  });

  it('Go to Handling Unit Editor and check expected qty of finished good', function() {
    cy.visitWindow('540189');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoLookupListField('M_Product_ID', finishedGoodName, finishedGoodName, false, false, null, true);
    applyFilters();

    cy.expectNumberOfRows(2);
    cy.selectRowByColumnAndValue({ column: qtyCuColumn, value: manufacturingQtyEntered }, false, false, false).should('have.length', 2);
  });
});

function selectHuAsSource(productValue, huValue) {
  cy.selectRowByColumnAndValue({ column: productColumn, value: productValue }, true);
  cy.log('after row selection');
  cy.executeQuickActionWithRightSideTable('WEBUI_PP_Order_HUEditor_Launcher');
  cy.selectRightTable().within(() => {
    cy.selectRowByColumnAndValue({ column: huSelectionCodeColumn, value: huValue }, false, true);
  });
  cy.executeQuickAction('WEBUI_PP_Order_HUEditor_Create_M_Source_HUs', true, false);
  cy.selectLeftTable().within(() => {
    cy.selectRowByColumnAndValue(createColumnAndValue(productValue, huValue, 'CU', null, huQty, packingStatusActive), false, true);
  });
  // used to close the right table
  cy.get('.panel-modal-header-title').click();
}

function createColumnAndValue(productValue, codeValue, typeValue, qtyPlanValue, qtyValue, packingStatusValue) {
  // eslint-disable-next-line
  const colAndVal = [
    new ColumnAndValue(productColumn, productValue),
    new ColumnAndValue(typeColumn, typeValue),
  ];
  if (qtyPlanValue) {
    colAndVal.push(new ColumnAndValue(qtyPlanColumn, qtyPlanValue));
  }
  if (qtyValue) {
    colAndVal.push(new ColumnAndValue(qtyColumn, qtyValue));
  }
  if (packingStatusValue) {
    colAndVal.push(new ColumnAndValue(pickingStatusColumn, packingStatusValue));
  }
  if (codeValue) {
    colAndVal.push(new ColumnAndValue(pickingCodeColumn, codeValue));
  }
  return colAndVal;
}
