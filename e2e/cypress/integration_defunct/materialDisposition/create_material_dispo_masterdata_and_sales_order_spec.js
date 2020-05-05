import { Product } from '../../support/utils/product';
import { BillOfMaterial, BillOfMaterialLine } from '../../support/utils/billOfMaterial';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { Builder } from '../../support/utils/builder';
import { BPartner } from '../../support/utils/bpartner';
import { getLanguageSpecific } from '../../support/utils/utils';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import { Warehouse } from '../../support/utils/warehouse';
import { DocumentStatusKey } from '../../support/utils/constants';

let plantName;

let warehouseName;

let priceSystemName;
let priceListName;
let priceListVersionName;

let customerName;

let productCategoryName;

let mainProductName;
let componentProductName;
let secondComponentProductName;

let firstBomName;
let secondBomName;

let poReference;

let firstBomMainProductId;

it('Read the fixture', function() {
  cy.fixture('materialDisposition/create_material_dispo_masterdata_and_sales_order_spec.json').then(f => {
    mainProductName = appendHumanReadableNow(f['mainProductName']);
    productCategoryName = appendHumanReadableNow(f['productCategoryName']);
    customerName = appendHumanReadableNow(f['customerName']);

    priceSystemName = appendHumanReadableNow(f['priceSystemName']);
    warehouseName = appendHumanReadableNow(f['warehouseName']);
    plantName = appendHumanReadableNow(f['plantName']);
    priceListName = appendHumanReadableNow(f['priceListName']);
    priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

    componentProductName = appendHumanReadableNow(f['componentProductName']);

    secondComponentProductName = appendHumanReadableNow(f['secondComponentProductName']);
    firstBomName = appendHumanReadableNow(f['firstBomName']);
    secondBomName = appendHumanReadableNow(f['secondBomName']);

    poReference = appendHumanReadableNow(f['poReference']);
  });
});

describe('Setup', function() {
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
    // currently broken
    it('Create cumstomer', function() {
      cy.fixture('sales/simple_customer.json').then(customerJson => {
        const bpartner = new BPartner({ ...customerJson, name: customerName });
        bpartner.apply();
      });
    });

    it('Create main product and price', function() {
      Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);
      Builder.createBasicProductEntities(productCategoryName, productCategoryName, priceListName, mainProductName, mainProductName /*productValue*/, 'Item' /*productType*/);
      cy.getCurrentWindowRecordId().then(id => {
        firstBomMainProductId = id;
      });
    });
  });

  describe('Create products, BOMs and planning data', function() {
    let secondBomMainProductId;
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
        cy.visitWindow('53007', firstBomMainProductId);
        cy.selectTab('PP_Product_Planning');
        cy.pressAddNewButton();
        cy.selectInListField('PP_Product_BOM_ID', mainProductName, true /*modal*/); // the lookup string currently does not contain the BOM's name, but one the product's name
        cy.writeIntoLookupListField('S_Resource_ID', plantName, plantName, false /*typeList*/, true /*modal*/);
        cy.selectInListField('IsManufactured', 'Yes', true /*modal*/);

        // we want the PP_Order for the first product to be created, but not completed
        cy.expectCheckboxValue('IsCreatePlan', true, true /*modal*/);
        cy.expectCheckboxValue('IsDocComplete', false, true /*modal*/);

        cy.pressDoneButton();
      });

      it("Create product planning for 2nd BOM's main product", function() {
        cy.visitWindow('53007', secondBomMainProductId);
        cy.selectTab('PP_Product_Planning');
        cy.pressAddNewButton();
        cy.selectInListField('PP_Product_BOM_ID', componentProductName, true /*modal*/); // the lookup string currently does not contain the BOM's name, but one the product's name
        cy.writeIntoLookupListField('S_Resource_ID', plantName, plantName, false /*typeList*/, true /*modal*/);
        cy.selectInListField('IsManufactured', 'Yes', true /*modal*/);

        // we want the PP_Order for the second product to be created, *and* completed
        // see TODO comment in the actual test further down
        cy.expectCheckboxValue('IsCreatePlan', true, true /*modal*/);
        cy.getCheckboxValue('IsDocComplete', true /*modal*/).then(checkBoxValue => {
          if (!checkBoxValue) {
            cy.clickOnCheckBox('IsDocComplete', true /*modal*/);
          }
        });

        cy.pressDoneButton();
      });
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
