export class BPartner {
  constructor(name) {
    cy.log(`BPartner - set name = ${name}`);
    this.name = name;
    this.isVendor = false;
    this.vendorPricingSystem = undefined;
    this.vendorDiscountSchema = undefined;
    this.customerDiscountSchema = undefined;
    this.customerDunning = undefined;
    this.isCustomer = false;
    this.bPartnerLocations = [];
    this.contacts = [];
    this.bank = undefined;
  }

  setName(name) {
    cy.log(`BPartner - set name = ${name}`);
    this.name = name;
    return this;
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

  setCustomerDunning(customerDunning) {
    cy.log(`BPartner - set customerDunning = ${customerDunning}`);
    this.customerDunning = customerDunning;
    return this;
  }

  setCustomer(isCustomer) {
    cy.log(`BPartner - set isCustomer = ${isCustomer}`);
    this.isCustomer = isCustomer;
    return this;
  }

  setPaymentTerm(paymentTerm) {
    cy.log(`BPartner - set paymentTerm = ${paymentTerm}`);
    this.paymentTerm = paymentTerm;
    return this;
  }

  setCustomerPricingSystem(customerPricingSystem) {
    cy.log(`BPartner - set customerPricingSystem = ${customerPricingSystem}`);
    this.customerPricingSystem = customerPricingSystem;
    return this;
  }

  setCustomerDiscountSchema(customerDiscountSchema) {
    cy.log(`BPartner - set customerDiscountSchema = ${customerDiscountSchema}`);
    this.customerDiscountSchema = customerDiscountSchema;
    return this;
  }

  setBank(bank) {
    cy.log(`BPartner - set Bank = ${bank}`);
    this.bank = bank;
    return this;
  }

  addLocation(bPartnerLocation) {
    cy.log(`BPartner - add location = ${JSON.stringify(bPartnerLocation)}`);
    this.bPartnerLocations.push(bPartnerLocation);
    return this;
  }

  clearLocations() {
    cy.log(`BPartner - clear locations`);
    this.bPartnerLocations = [];
    return this;
  }

  addContact(contact) {
    cy.log(`BPartner - add contact = ${JSON.stringify(contact)}`);
    this.contacts.push(contact);
    return this;
  }

  clearContacts() {
    cy.log(`BPartner - clear contacts`);
    this.contacts = [];
    return this;
  }

  apply() {
    cy.log(`BPartner - apply - START (name=${this.name})`);
    applyBPartner(this);
    cy.log(`BPartner - apply - END (name=${this.name})`);
    return this;
  }

  static applyBank(bank) {
    cy.selectTab('C_BP_BankAccount');
    cy.pressAddNewButton();
    cy.writeIntoLookupListField('C_Bank_ID', bank, bank, false, true);
    cy.writeIntoStringField('A_Name', 'Test Account', true);
    cy.pressDoneButton();
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
  describe(`Create new bPartner ${bPartner.name}`, function () {
    cy.get('.avatar').then(() => {
      cy.visitWindow('123', 'NEW');
      cy.writeIntoStringField('CompanyName', bPartner.name);
      cy.writeIntoStringField('Name2', bPartner.name);
      if (bPartner.isVendor || bPartner.vendorDiscountSchema || bPartner.vendorPricingSystem) {
        cy.selectTab('Vendor');
        cy.selectSingleTabRow();

        cy.openAdvancedEdit();
        cy.getCheckboxValue('IsVendor').then((isVendorValue) => {
          if (bPartner.isVendor && !isVendorValue) {
            cy.clickOnCheckBox('IsVendor', true, true /*modal*/);
          }
        });
        if (bPartner.vendorPricingSystem) {
          cy.writeIntoLookupListField('PO_PricingSystem_ID', bPartner.vendorPricingSystem, bPartner.vendorPricingSystem, false, true);
        }
        if (bPartner.vendorDiscountSchema) {
          cy.selectInListField('PO_DiscountSchema_ID', bPartner.vendorDiscountSchema, true /*modal*/);
        }
        cy.pressDoneButton();
      }

      if (bPartner.isCustomer || bPartner.customerPricingSystem) {
        cy.selectTab('Customer');
        cy.selectSingleTabRow();

        cy.openAdvancedEdit();
        cy.getCheckboxValue('IsCustomer').then((isCustomerValue) => {
          if (bPartner.isCustomer && !isCustomerValue) {
            cy.clickOnCheckBox('IsCustomer', true, true /*modal*/);
          }
        });
        if (bPartner.customerDiscountSchema) {
          cy.selectInListField('M_DiscountSchema_ID', bPartner.customerDiscountSchema, true /*modal*/);
        }
        if (bPartner.customerPricingSystem) {
          cy.writeIntoLookupListField('M_PricingSystem_ID', bPartner.customerPricingSystem, bPartner.customerPricingSystem, false, true);
        }
        if (bPartner.customerDunning) {
          cy.selectInListField('C_Dunning_ID', bPartner.customerDunning, true /*modal*/);
        }
        if (bPartner.paymentTerm) {
          // cy.selectInListField('C_PaymentTerm_ID', getLanguageSpecific(bPartner, 'paymentTerm'), true); // todo this doesn't work. it breaks the login. WHYYYYYYYYYYYYY????
          cy.selectInListField('C_PaymentTerm_ID', bPartner.paymentTerm, true);
        }
        cy.pressDoneButton();
      }

      // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
      if (bPartner.bPartnerLocations.length > 0) {
        bPartner.bPartnerLocations.forEach(function (bPartnerLocation) {
          applyLocation(bPartnerLocation);
        });
        cy.get('table tbody tr').should('have.length', bPartner.bPartnerLocations.length);
      }
      if (bPartner.contacts.length > 0) {
        bPartner.contacts.forEach(function (bPartnerContact) {
          applyContact(bPartnerContact);
        });
        cy.get('table tbody tr').should('have.length', bPartner.contacts.length);
      }
      if (bPartner.bank) {
        BPartner.applyBank(bPartner.bank);
      }
    });
  });
}

function applyLocation(bPartnerLocation) {
  cy.selectTab('C_BPartner_Location');
  cy.pressAddNewButton();
  cy.log(`applyLocation - bPartnerLocation.name = ${bPartnerLocation.name}`);
  cy.writeIntoStringField('Name', `${bPartnerLocation.name}`, true /*modal*/, false, true);
  cy.get('.panel-modal-header-title').click();

  cy.editAddress('C_Location_ID', function (url) {
    cy.writeIntoStringField('Address1', ' ', null, url);
    cy.writeIntoStringField('City', bPartnerLocation.city, null, url);
    cy.writeIntoLookupListField('C_Country_ID', bPartnerLocation.country, bPartnerLocation.country, false /*typeList */, false /*modal THIS MUST BE FALSE EVEN IF IT'S A MODAL!*/, url);
  });
  cy.get('.form-field-Address').should('contain', bPartnerLocation.city);
  cy.pressDoneButton();
}

function applyContact(bPartnerContact) {
  cy.selectTab('AD_User');
  cy.pressAddNewButton();
  cy.writeIntoStringField('Firstname', bPartnerContact.firstName, true /*modal*/);
  cy.writeIntoStringField('Lastname', bPartnerContact.lastName, true /*modal*/);

  if (bPartnerContact.isDefaultContact) {
    cy.clickOnCheckBox('IsDefaultContact', true /*expectedPatchValue*/, true /*modal*/);
  }
  cy.pressDoneButton();
}
