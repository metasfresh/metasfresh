import React, { Component } from 'react';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';
import BarcodeScanner from './BarcodeScanner';
import ConfirmActivity from './ConfirmActivity';
//import PickProductsActivity from './PickProductsActivity';

class WFProcess extends Component {
  render() {
    const { wfProcessId, activities } = this.props;
    console.log(activities);

    const barcodeMockProps = {
      activityId: '1',
      caption: 'Scan picking slot',
      componentType: 'common/scanBarcode',
      readonly: true,
      componentProps: {
        barcodeCaption: null,
      },
    };

    return (
      <div className="pick-products-container">
        <div className="title is-4 header-caption">WFProcess Caption HEADER</div>
        <div className="pick-products-active-line">headers</div>
        {activities.length > 0 &&
          activities.map((activityItem) => {
            let uniqueId = uuidv4();
            switch (activityItem.componentType) {
              case 'common/scanBarcode':
                return <BarcodeScanner key={uniqueId} id={uniqueId} {...barcodeMockProps} />;
              //   case 'picking/pickProducts':
              //     return <PickProductsActivity key={uniqueId} id={uniqueId} />;
              case 'common/confirmButton':
                return <ConfirmActivity key={uniqueId} id={uniqueId} wfProcessId={wfProcessId} {...activityItem} />;
            }
          })}
      </div>
    );
  }
}

WFProcess.propTypes = {
  activities: PropTypes.array,
  headerProperties: PropTypes.object,
};

export default WFProcess;
