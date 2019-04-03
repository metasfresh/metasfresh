export class BPartner {
  constructor(name) {
    cy.log(`BPartner - set name = ${name}`);
    this.name = name;
    this.isVendor = false;
    this.vendorPricingSystem = undefined;
    this.vendorDiscountSchema = undefined;
    this.isCustomer = false;
    this.bPartnerLocations = [];
    this.contacts = [];
  }

  setVendor(isVendor) {
    cy.log(`BPartner - set isVendor = ${isVendor}`);
    this.isVendor = isVendor;
    return this;
  }

  setVendorPricingSystem(vendorPricingSystem) {
    cy.log(`BPartner - set vendorPricingSystem = ${vendorPricingSystem}`);
    this.vendorPricingSystem = vendorPricingSystem;
    return this;
  }

  setVendorDiscountSchema(vendorDiscountSchema) {
    cy.log(`BPartner - set vendorDiscountSchema = ${vendorDiscountSchema}`);
    this.vendorDiscountSchema = vendorDiscountSchema;
    return this;
  }

  setCustomer(isCustomer) {
    cy.log(`BPartner - set isCustomer = ${isCustomer}`);
    this.isCustomer = isCustomer;
    return this;
  }

  addLocation(bPartnerLocation) {
    cy.log(`BPartner - add location = ${JSON.stringify(bPartnerLocation)}`);
    this.bPartnerLocations.push(bPartnerLocation);
    return this;
  }

  addContact(contact) {
    cy.log(`BPartner - add contact = ${JSON.stringify(contact)}`);
    this.contacts.push(contact);
    return this;
  }

  apply() {
    cy.log(`BPartner - apply - START (name=${this.name})`);
    applyBPartner(this);
    cy.log(`BPartner - apply - END (name=${this.name})`);
    return this;
  }
}

export class BPartnerLocation {
  constructor(name) {
    cy.log(`BPartnerLocation - set name = ${name}`);
    this.name = name;
  }

  setCity(city) {
    cy.log(`BPartnerLocation - set city = ${city}`);
    this.city = city;
    return this;
  }

  setCountry(country) {
    cy.log(`BPartnerLocation - set country = ${country}`);
    this.country = country;
    return this;
  }
}

export class BPartnerContact {
  constructor() {
    this.isDefaultContact = false;
  }

  setFirstName(firstName) {
    cy.log(`BPartnerContact - set firstName = ${firstName}`);
    this.firstName = firstName;
    return this;
  }

  setLastName(lastName) {
    cy.log(`BPartnerContact - set lastName = ${lastName}`);
    this.lastName = lastName;
    return this;
  }

  setDefaultContact(isDefaultContact) {
    cy.log(`BPartnerContact - set defaultContact = ${isDefaultContact}`);
    this.isDefaultContact = isDefaultContact;
    return this;
  }
}

function applyBPartner(bPartner) {
  describe(`Create new bPartner ${bPartner.name}`, function() {
    cy.visitWindow('123', 'NEW');
    cy.writeIntoStringField('CompanyName', bPartner.name);
    cy.writeIntoStringField('Name2', bPartner.name);

    if (bPartner.isVendor || bPartner.vendorDiscountSchema || bPartner.vendorPricingSystem) {
      cy.selectTab('Vendor');
      cy.selectSingleTabRow();

      cy.openAdvancedEdit();
      if (bPartner.isVendor) {
        cy.clickOnCheckBox('IsVendor');
      }
      if (bPartner.vendorPricingSystem) {
        cy.selectInListField('PO_PricingSystem_ID', bPartner.vendorPricingSystem, bPartner.vendorPricingSystem);
      }
      if (bPartner.vendorDiscountSchema) {
        cy.selectInListField('PO_DiscountSchema_ID', bPartner.vendorDiscountSchema, bPartner.vendorDiscountSchema);
      }
      cy.pressDoneButton();
    }

    if (bPartner.isCustomer) {
      cy.selectTab('Customer');
      cy.selectSingleTabRow();

      cy.openAdvancedEdit();
      cy.clickOnCheckBox('IsCustomer');
      cy.pressDoneButton();
    }

    // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
    if (bPartner.bPartnerLocations.length > 0) {
      bPartner.bPartnerLocations.forEach(function(bPartnerLocation) {
        applyLocation(bPartnerLocation);
      });
      cy.get('table tbody tr').should('have.length', bPartner.bPartnerLocations.length);
    }
    if (bPartner.contacts.length > 0) {
      bPartner.contacts.forEach(function(bPartnerContact) {
        applyContact(bPartnerContact);
      });
      cy.get('table tbody tr').should('have.length', bPartner.contacts.length);
    }
  });
}

function applyLocation(bPartnerLocation) {
  cy.selectTab('C_BPartner_Location');
  cy.pressAddNewButton();
  //cy.log(`applyLocation - bPartnerLocation.name = ${bPartnerLocation.name}`);
  
  // Location Name field is readonly (cannot be overwritten)
  //cy.writeIntoStringField('Name', bPartnerLocation.name);

  cy.editAddress('C_Location_ID', function(url) {
    //cy.writeIntoStringField('City', bPartnerLocation.city);
    //cy.writeIntoLookupListField('C_Country_ID', bPartnerLocation.country, bPartnerLocation.country);
    cy.writeIntoStringField('City', bPartnerLocation.city, null, url);
    cy.writeIntoLookupListField('C_Country_ID', bPartnerLocation.country, bPartnerLocation.country, null, url);
  });
  cy.get('.form-field-Address').should('contain', bPartnerLocation.city);
  cy.pressDoneButton();
}

function applyContact(bPartnerContact) {
  cy.selectTab('AD_User');
  cy.pressAddNewButton();
  cy.writeIntoStringField('Firstname', bPartnerContact.firstName);
  cy.writeIntoStringField('Lastname', bPartnerContact.lastName);

  if (bPartnerContact.isDefaultContact) {
    cy.clickOnCheckBox('IsDefaultContact');
  }
  cy.pressDoneButton();
}
