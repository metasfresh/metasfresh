import React from 'react';
import { observer } from 'mobx-react';
import { useParams } from 'react-router-dom';

import View from './View';

interface RouteParams {
  quotationId?: string;
}

const RfQDetails: React.FunctionComponent = observer(() => {
  const { quotationId } = useParams<RouteParams>();
  return (
    <View>
      <div className="mt-1 p-4">
        <p>Quotation: {quotationId}</p>
      </div>
    </View>
  );
});

export default RfQDetails;
