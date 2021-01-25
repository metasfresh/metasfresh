import { types, flow } from 'mobx-state-tree';

import { fetchRFQuotations } from '../api';
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
    return {
      fetchRFQs,
    };
  });

export default RFQList;
