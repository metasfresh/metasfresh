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
}

export const inventory = new Inventory();
