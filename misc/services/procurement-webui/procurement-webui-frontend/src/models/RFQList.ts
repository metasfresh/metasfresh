import { types, flow, getSnapshot } from 'mobx-state-tree';

import { fetchRFQuotations, postRfQ } from '../api';
import { RFQ, Quantity } from './RFQ';

export const RFQList = types
  .model({
    quotations: types.optional(types.array(RFQ), []),
  })
  .actions((self) => {
    const fetchRFQs = flow(function* fetchQuotations() {
      try {
        const {
          data: { rfqs },
        } = yield fetchRFQuotations();

        setInitialData(rfqs);
      } catch (error) {
        console.error('Failed to fetch', error);
      }
    });
    const setInitialData = function setInitialData(dataToSet: any) {
      const elements = dataToSet.map((el) => {
        return RFQ.create({
          ...el,
          quantities: el.quantities.map((quantity) => Quantity.create({ ...quantity })),
        });
      });

      self.quotations = elements;
    };

    const updateRfQ = flow(function* updateSingleRfQ({
      price,
      quantities,
      rfqId,
    }: {
      price?: number;
      quantities?: { date: string; qtyPromised: number }[];
      rfqId: string;
    }) {
      try {
        const response = price ? yield postRfQ({ price, rfqId }) : yield postRfQ({ price, quantities, rfqId });
        const { data } = response;
        getSnapshot(self.quotations).forEach((item, index) => {
          if (item.rfqId === data.rfqId) {
            self.quotations[index] = data;
          }
        });
      } catch (error) {
        console.error('Failed to update', error);
      }
    });
    return {
      fetchRFQs,
      updateRfQ,
    };
  })
  .views((self) => ({
    get getQuotations() {
      return self.quotations;
    },
    findQuotationById(quotationId: string) {
      return self.quotations.find((qItem) => qItem.rfqId === quotationId);
    },
  }));

export default RFQList;
