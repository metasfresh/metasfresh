import {Pricesystem} from "./pricesystem";
import {PriceList, PriceListVersion} from "./pricelist";

export class Builder {

  /**
   * Use this when you aren't interested in configuring anything (except for the name) for the PriceSystem, PriceList or PriceListVersion, but you only need them to exist.
   *
   * - Most tests should use this builder instead of copy pasting everything in the test file.
   *
   * - Only the tests which need customised Price* types should create their own (by copying the contents of this method and modifying as needed).
   * @param priceSystemName
   * @param priceListVersionName
   * @param priceListName
   */
  static createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName) {
    cy.fixture('price/pricesystem.json').then(priceSystemJson => {
      Object.assign(new Pricesystem(/* useless to set anything here since it's replaced by the fixture */), priceSystemJson)
        .setName(priceSystemName)
        .apply();
    });

    let priceListVersion;
    cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
      priceListVersion = Object.assign(new PriceListVersion(/* useless to set anything here since it's replaced by the fixture */), priceListVersionJson)
        .setName(priceListVersionName)
    });

    cy.fixture('price/pricelist.json').then(pricelistJson => {
      Object.assign(new PriceList(/* useless to set anything here since it's replaced by the fixture */), pricelistJson)
        .setName(priceListName)
        .setPriceSystem(priceSystemName)
        .addPriceListVersion(priceListVersion)
        .apply();
    });
  }

}
