import React, { Component } from 'react';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';
import BarcodeScanner from './BarcodeScanner';
import ConfirmActivity from './ConfirmActivity';
import PickProductsActivity from './PickProductsActivity';

class WFProcess extends Component {
  scanActivityPostDetection = (detectedCode) => {
    console.log('Detected code -:', detectedCode);
  };

  render() {
    const { wfProcessId, activities, status } = this.props;
    const { activities: activitiesState } = status;

    return (
      <div className="pick-products-container">
        <div className="title is-4 header-caption">WFProcess Caption HEADER</div>
        <div className="pick-products-active-line header-caption">custom headers</div>
        {activities.length > 0 &&
          activities.map((activityItem) => {
            let uniqueId = uuidv4();
            switch (activityItem.componentType) {
              case 'common/scanBarcode':
                return (
                  <BarcodeScanner
                    key={uniqueId}
                    id={uniqueId}
                    {...activityItem}
                    onDetection={this.scanActivityPostDetection}
                  />
                );
              case 'picking/pickProducts':
                return (
                  <PickProductsActivity
                    key={uniqueId}
                    id={uniqueId}
                    wfProcessId={wfProcessId}
                    activityId={activityItem.id}
                    activityState={activitiesState[activityItem.activityId]}
                    {...activityItem}
                  />
                );
              case 'common/confirmButton':
                return <ConfirmActivity key={uniqueId} id={uniqueId} wfProcessId={wfProcessId} {...activityItem} />;
            }
          })}
      </div>
    );
  }
}

WFProcess.propTypes = {
  wfProcessId: PropTypes.string,
  activities: PropTypes.array,
  headerProperties: PropTypes.object,
  status: PropTypes.object,
};

export default WFProcess;
