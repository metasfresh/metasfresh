import { types } from 'mobx-state-tree';

export const Quantity = types.model({
  confirmedByUser: types.boolean,
  date: types.string,
  dayCaption: types.string,
  qtyPromised: types.number,
  qtyPromisedRendered: types.string,
});

export const RFQ = types.model({
  confirmedByUser: types.boolean,
  dateClose: types.string,
  dateEnd: types.string,
  dateStart: types.string,
  packingInfo: types.string,
  price: types.number,
  priceRendered: types.string,
  productName: types.string,
  qtyPromised: types.string,
  qtyRequested: types.string,
  quantities: types.optional(types.array(Quantity), []),
  rfqId: types.string,
});
