import { Product } from '../../support/utils/product';
import { BillOfMaterial, BillOfMaterialLine } from '../../support/utils/billOfMaterial';
import { appendHumanReadableNow, getLanguageSpecific } from '../../support/utils/utils';
import { Builder } from '../../support/utils/builder';
import { BPartner } from '../../support/utils/bpartner';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import { Warehouse } from '../../support/utils/warehouse';
import { DocumentStatusKey } from '../../support/utils/constants';

let plantName;

let warehouseName;

let priceSystemName;
let priceListName;

let customerName;

let productCategoryName;

let mainProductName;
let componentProductName;
let secondComponentProductName;

let firstBomName;
let secondBomName;

let poReference;

let firstBomMainProductId;
let secondBomMainProductId;

it('Read the fixture', function() {
  cy.fixture('materialDisposition/create_material_dispo_masterdata_and_sales_order_spec.json').then(f => {
    const dateOverride = null;
    mainProductName = appendHumanReadableNow(f['mainProductName'], dateOverride);
    productCategoryName = appendHumanReadableNow(f['productCategoryName'], dateOverride);
    customerName = appendHumanReadableNow(f['customerName'], dateOverride);

    priceSystemName = appendHumanReadableNow(f['priceSystemName'], dateOverride);
    warehouseName = appendHumanReadableNow(f['warehouseName'], dateOverride);
    plantName = appendHumanReadableNow(f['plantName'], dateOverride);
    priceListName = appendHumanReadableNow(f['priceListName'], dateOverride);

    componentProductName = appendHumanReadableNow(f['componentProductName'], dateOverride);

    secondComponentProductName = appendHumanReadableNow(f['secondComponentProductName'], dateOverride);
    firstBomName = appendHumanReadableNow(f['firstBomName'], dateOverride);
    secondBomName = appendHumanReadableNow(f['secondBomName'], dateOverride);

    poReference = appendHumanReadableNow(f['poReference'], dateOverride);
  });
});

describe('Create plant and warehouse', function() {
  it('Create plant', function() {
    cy.visitWindow('53004', 'NEW');
    cy.writeIntoStringField('Name', plantName);
    cy.selectInListField('S_ResourceType_ID', 'Produktionsressource');

    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.selectInListField('ManufacturingResourceType', getLanguageSpecific(miscDictionary, 'S_Resource_MFG_Type_Plant'));
    });
    cy.writeIntoStringField('PlanningHorizon', 1);
  });

  it('Create warehouse', function() {
    cy.fixture('misc/warehouse.json').then(warehouseJson => {
      Object.assign(new Warehouse(), warehouseJson)
        .setName(warehouseName)
        .setValue(warehouseName)
        .setPlant(plantName)
        .apply();
    });
  });
});

describe('Create customer, main product and price', function() {
  it('Create main product and price', function() {
    Builder.createBasicPriceEntities(priceSystemName, null, priceListName, true);
    Builder.createBasicProductEntities(productCategoryName, productCategoryName, priceListName, mainProductName, /*productValue=*/ mainProductName, /*productType=*/ 'Item');
    cy.getCurrentWindowRecordId().then(id => {
      firstBomMainProductId = id;
    });
  });

  it('Create customer', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: customerName });
      bpartner.setCustomerPricingSystem(priceSystemName);
      bpartner.apply();
    });
  });
});

describe('Create products, BOMs and planning data', function() {
  it("Create 1st BOM's component product which is 2nd BOM's main product", function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(componentProductName)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .setStocked(true)
        .setPurchased(false)
        .setSold(false)
        .setDescription(`Component of ${firstBomName}; main product of ${secondBomName}`)
        .apply();
    });
    cy.getCurrentWindowRecordId().then(id => {
      secondBomMainProductId = id;
    });
  });

  describe('Create products, BOMs and planning data', function() {
    describe('Create and verify 1st BOM', function() {
      it('Create 1st BOM', function() {
        cy.fixture('product/bill_of_material.json').then(billMaterialJson => {
          Object.assign(new BillOfMaterial(), billMaterialJson)
            .setName(firstBomName)
            .setProduct(mainProductName)
            // eslint-disable-next-line prettier/prettier
            .addLine(new BillOfMaterialLine().setProduct(componentProductName).setQuantity(10))
            .apply();
        });
      });
      it('Verify 1st BOM', function() {
        expect(firstBomMainProductId).to.be.ok;
        cy.executeHeaderActionWithDialog('PP_Product_BOM');
        cy.pressStartButton();
        cy.visitWindow('140', firstBomMainProductId);
        cy.getCheckboxValue('IsBOM').then(checkBoxValue => {
          expect(checkBoxValue).to.eq(true);
        });
        cy.getCheckboxValue('IsVerified').then(checkBoxValue => {
          expect(checkBoxValue).to.eq(true);
        });
      });
    });
  });

  describe('Create and verify 2nd BOM', function() {
    it("Create 2nd BOM's component product", function() {
      cy.fixture('product/simple_product.json').then(productJson => {
        Object.assign(new Product(), productJson)
          .setName(secondComponentProductName)
          .setProductCategory(productCategoryName + '_' + productCategoryName)
          .setStocked(true)
          .setPurchased(false)
          .setSold(false)
          .setDescription(`Component of ${secondBomName}`)
          .apply();
      });
    });

    // create 2nd BOM
    it('Create 2nd BOM', function() {
      cy.fixture('product/bill_of_material.json').then(billMaterialJson => {
        Object.assign(new BillOfMaterial(), billMaterialJson)
          .setName(secondBomName)
          .setProduct(componentProductName)
          // eslint-disable-next-line prettier/prettier
          .addLine(new BillOfMaterialLine().setProduct(secondComponentProductName).setQuantity(10))
          .apply();
      });
    });
    it('Verify 2nd BOM', function() {
      expect(secondBomMainProductId).to.be.ok;
      cy.executeHeaderActionWithDialog('PP_Product_BOM');
      cy.pressStartButton();
      cy.visitWindow('140', secondBomMainProductId);
      cy.getCheckboxValue('IsBOM').then(checkBoxValue => {
        expect(checkBoxValue).to.eq(true);
      });
      cy.getCheckboxValue('IsVerified').then(checkBoxValue => {
        expect(checkBoxValue).to.eq(true);
      });
    });
  });

  describe('Create product planning records', function() {
    it("Create product planning for 1st BOM's main product", function() {
      // todo maybe it makes sense to create a new class Product Planning, in which this duplicate code should live
      cy.visitWindow(540750, 'NEW');
      cy.writeIntoLookupListField('M_Product_ID', mainProductName, mainProductName);
      cy.selectInListField('M_Warehouse_ID', warehouseName);

      cy.selectInListField('PP_Product_BOM_ID', mainProductName);
      // cy.selectInListField('AD_Workflow_ID', ); // todo what is a workflow? do we need one for the test?
      cy.writeIntoLookupListField('S_Resource_ID', plantName, plantName);
      cy.selectInListField('IsManufactured', 'Yes');

      // we want the PP_Order for the first product to be created, but not completed
      cy.expectCheckboxValue('IsCreatePlan', true);
      cy.expectCheckboxValue('IsDocComplete', false);
    });

    it("Create product planning for 2nd BOM's main product", function() {
      cy.visitWindow(540750, 'NEW');
      cy.writeIntoLookupListField('M_Product_ID', componentProductName, componentProductName);
      cy.selectInListField('M_Warehouse_ID', warehouseName);

      cy.selectInListField('PP_Product_BOM_ID', componentProductName);
      // cy.selectInListField('AD_Workflow_ID', ); // todo what is a workflow? do we need one for the test?
      cy.writeIntoLookupListField('S_Resource_ID', plantName, plantName);
      cy.selectInListField('IsManufactured', 'Yes');

      // we want the PP_Order for the first product to be created, but not completed
      cy.expectCheckboxValue('IsCreatePlan', true);
      cy.setCheckBoxValue('IsDocComplete', true);
      cy.expectCheckboxValue('IsDocComplete', true);
    });
  });
});

describe('Perform the actual test', function() {
  it("Create sales order with 1st BOM's main product", function() {
    new SalesOrder()
      .setBPartner(customerName)
      .setPriceSystem(priceSystemName)
      .setWarehouse(warehouseName)
      .setPoReference(poReference)
      .addLine(new SalesOrderLine().setProduct(mainProductName).setQuantity(1))
      .apply();
    cy.completeDocument();
  });
  it("Expect one production order for 1st BOM's main product", function() {
    cy.openReferencedDocuments('PP_Order');

    // expect one drafted production order, because the first product's product planning record has IsDocComplete=false
    cy.expectNumberOfRows(1)
      .selectNthRow(0)
      .dblclick();

    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      const draftedStatus = getLanguageSpecific(miscDictionary, DocumentStatusKey.Drafted);
      cy.log(`Verify that the production order's doc status is ${draftedStatus}`);
      cy.contains('.meta-dropdown-toggle .tag', draftedStatus);
    });
  });
  // TODO
  // * verify the material dispo records (maybe pull snapshots as there are a lot of fields to verify)
  // * complete the production order
  // * verify that the system created a 2nd production order and directly completed it
});
