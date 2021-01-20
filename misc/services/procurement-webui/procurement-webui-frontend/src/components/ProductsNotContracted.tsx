import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';
import { translate } from '../utils/translate';
import { RootInstance } from '../models/Store';

interface Props {
  store?: RootInstance;
}

@inject('store')
@observer
class ProductsNotContracted extends React.Component<Props> {
  render(): ReactElement {
    const { store } = this.props;

    if (!store) {
      return null;
    }

    return (
      <div className="mt-4">
        <div className="box">{translate('SelectProductView.showMeNotContractedButton')}</div>
      </div>
    );
  }
}

export default ProductsNotContracted;
