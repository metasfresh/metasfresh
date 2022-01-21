import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { toastError } from '../../../utils/toast';

import CodeScanner from '../../../containers/activities/scan/CodeScanner';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

import { getHUByBarcode } from '../api';
import { clearLoadedData, handlingUnitLoaded } from '../actions';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { huManagerDisposeLocation } from '../routes';
import { HUInfoComponent } from '../components/HUInfoComponent';

class HUManagerScreen extends PureComponent {
  constructor(props) {
    super(props);
  }

  onHUBarcodeScanned = ({ scannedBarcode }) => {
    const { dispatch } = this.props;

    getHUByBarcode(scannedBarcode)
      .then((handlingUnitInfo) => {
        dispatch(handlingUnitLoaded({ handlingUnitInfo }));
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  onDisposeClick = () => {
    const { dispatch } = this.props;
    dispatch(push(huManagerDisposeLocation()));
  };

  onScanAgainClick = () => {
    const { dispatch } = this.props;
    dispatch(clearLoadedData());
  };

  render() {
    const { handlingUnitInfo } = this.props;

    if (handlingUnitInfo && handlingUnitInfo.id) {
      return (
        <>
          <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />
          {this.renderHandlingUnitActions()}
        </>
      );
    } else {
      return <CodeScanner onBarcodeScanned={this.onHUBarcodeScanned} />;
    }
  }

  renderHandlingUnitActions = () => {
    return (
      <div className="pt-3 section">
        <ButtonWithIndicator
          caption={counterpart.translate('huManager.action.dispose.buttonCaption')}
          onClick={this.onDisposeClick}
        />
        <ButtonWithIndicator
          caption={counterpart.translate('huManager.action.scanAgain.buttonCaption')}
          onClick={this.onScanAgainClick}
        />
      </div>
    );
  };
}

const mapStateToProps = (globalState) => {
  return {
    handlingUnitInfo: getHandlingUnitInfoFromGlobalState(globalState),
  };
};

HUManagerScreen.propTypes = {
  handlingUnitInfo: PropTypes.object,
  dispatch: PropTypes.func.isRequired,
};

export default connect(mapStateToProps)(HUManagerScreen);
