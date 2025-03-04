import React, { useCallback, useState } from 'react';
import counterpart from 'counterpart';
import {
  changeKPIItem,
  changeTargetIndicatorsItem,
} from '../../actions/DashboardActions';
import PropTypes from 'prop-types';
import { debounce } from 'lodash';

export const ChartOptions = ({
  id,
  caption: captionParam,
  isIndicator,
  onClose,
}) => {
  const [caption, setCaption] = useState(captionParam);

  const patchDashboardItem = (path, value) => {
    const values = { [path]: value };
    return isIndicator
      ? changeTargetIndicatorsItem(id, values)
      : changeKPIItem(id, values);
  };
  const patchDashboardItemDebounced = useCallback(
    debounce(patchDashboardItem, 500),
    []
  );

  const setState = (path, value) => {
    if (path === 'caption') setCaption(value);
  };

  const onFieldChanged = (path, value) => {
    setState(path, value);
    patchDashboardItemDebounced(path, value);
  };

  return (
    <div className="chart-options-overlay">
      <div className="chart-options-wrapper">
        <div className="chart-options">
          <div className="form-group">
            <label>
              {counterpart.translate('dashboard.item.settings.caption')}
            </label>
            <input
              className="input-options input-secondary"
              value={caption}
              onChange={(e) => onFieldChanged('caption', e.target.value)}
            />
          </div>
        </div>
        <div className="chart-options-button-wrapper">
          <button
            className="btn btn-meta-outline-secondary btn-sm"
            onClick={() => onClose()}
          >
            OK
          </button>
        </div>
      </div>
    </div>
  );
};

ChartOptions.propTypes = {
  id: PropTypes.number.isRequired,
  caption: PropTypes.string.isRequired,
  isIndicator: PropTypes.bool.isRequired,
  onClose: PropTypes.func.isRequired,
};
