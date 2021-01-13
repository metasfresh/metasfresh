import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';

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
        <div className="box">Products not contracted ..</div>
      </div>
    );
  }
}

export default ProductsNotContracted;
