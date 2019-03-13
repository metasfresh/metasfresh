export class BPartner {
  constructor(builder) {
    this.name = builder.name;
    this.isVendor = builder.isVendor;
    this.vendorPricingSystem = builder.vendorPricingSystem;
    this.vendorDiscountSchema = builder.vendorDiscountSchema;
    this.isCustomer = builder.isCustomer;
    this.locations = builder.bPartnerLocations;
    this.contacts = builder.contacts;
  }

  apply() {
    cy.log(`BPartner - apply - START (name=${this.name})`);
    applyBPartner(this);
    cy.log(`BPartner - apply - END (name=${this.name})`);
    return this;
  }

  static get builder() {
    class Builder {
      constructor(name) {
        cy.log(`BPartnerBuilder - set name = ${name}`);
        this.name = name;
        this.isVendor = false;
        this.vendorPricingSystem = undefined;
        this.vendorDiscountSchema = undefined;
        this.isCustomer = false;
        this.bPartnerLocations = [];
        this.contacts = [];
      }

      setVendor(isVendor) {
        cy.log(`BPartnerBuilder - set isVendor = ${isVendor}`);
        this.isVendor = isVendor;
        return this;
      }

      setVendorPricingSystem(vendorPricingSystem) {
        cy.log(`BPartnerBuilder - set vendorPricingSystem = ${vendorPricingSystem}`);
        this.vendorPricingSystem = vendorPricingSystem;
        return this;
      }

      setVendorDiscountSchema(vendorDiscountSchema) {
        cy.log(`BPartnerBuilder - set vendorDiscountSchema = ${vendorDiscountSchema}`);
        this.vendorDiscountSchema = vendorDiscountSchema;
        return this;
      }

      setCustomer(isCustomer) {
        cy.log(`BPartnerBuilder - set isCustomer = ${isCustomer}`);
        this.isCustomer = isCustomer;
        return this;
      }

      addLocation(bPartnerLocation) {
        cy.log(`BPartnerBuilder - add location = ${JSON.stringify(bPartnerLocation)}`);
        this.bPartnerLocations.push(bPartnerLocation);
        return this;
      }

      addContact(contact) {
        cy.log(`BPartnerBuilder - add contact = ${JSON.stringify(contact)}`);
        this.contacts.push(contact);
        return this;
      }

      build() {
        return new BPartner(this);
      }
    }
    return Builder;
  }
}

export class BPartnerLocation {
  constructor(builder) {
    this.name = builder.name;
    this.city = builder.city;
    this.country = builder.country;
  }

  static get builder() {
    class Builder {
      constructor(name) {
        cy.log(`BPartnerLocationBuilder - set name = ${name}`);
        this.name = name;
      }

      setCity(city) {
        cy.log(`BPartnerLocationBuilder - set city = ${city}`);
        this.city = city;
        return this;
      }

      setCountry(country) {
        cy.log(`BPartnerLocationBuilder - set country = ${country}`);
        this.country = country;
        return this;
      }

      build() {
        return new BPartnerLocation(this);
      }
    }
    return Builder;
  }
}

export class BPartnerContact {
  constructor(builder) {
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.isDefaultContact = builder.isDefaultContact;
  }

  static get builder() {
    class Builder {
      constructor() {
        this.isDefaultContact = false;
      }

      setFirstName(firstName) {
        cy.log(`BPartnerContactBuilder - set firstName = ${firstName}`);
        this.firstName = firstName;
        return this;
      }

      setLastName(lastName) {
        cy.log(`BPartnerContactBuilder - set lastName = ${lastName}`);
        this.lastName = lastName;
        return this;
      }

      setDefaultContact(isDefaultContact) {
        cy.log(`BPartnerContactBuilder - set defaultContact = ${isDefaultContact}`);
        this.isDefaultContact = isDefaultContact;
        return this;
      }

      build() {
        return new BPartnerContact(this);
      }
    }
    return Builder;
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
    if (bPartner.locations.length > 0) {
      bPartner.locations.forEach(function(bPartnerLocation) {
        applyLocation(bPartnerLocation);
      });
      cy.get('table tbody tr').should('have.length', bPartner.locations.length);
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
  cy.writeIntoStringField('Name', bPartnerLocation.name);

  cy.editAddress('C_Location_ID', function() {
    cy.writeIntoStringField('City', bPartnerLocation.city);
    cy.writeIntoLookupListField('C_Country_ID', bPartnerLocation.country, bPartnerLocation.country);
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
