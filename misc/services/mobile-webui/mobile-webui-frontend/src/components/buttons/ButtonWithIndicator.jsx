import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import * as CompleteStatus from '../../constants/CompleteStatus';
import HazardIcon from '../HazardIcon';
import AllergenIcon from '../AllergenIcon';
import * as uiTrace from '../../utils/ui_trace';
import { trl } from '../../utils/translations';
import { computeId, computeTestId } from '../../utils/testing_support';
import { WorkflowLauncherIndicator } from '../../constants/WorkflowLauncherIndicator';

const SYMBOLS_SIZE_PX = 25;

const ButtonWithIndicator = ({
  id: idParam,
  testId: testIdParam,
  caption: captionParam,
  captionKey,
  showWarningSign,
  typeFASIconName,
  hazardSymbols = null,
  allergens = null,
  isDanger,
  indicator1,
  indicator2,
  completeStatus,
  disabled,
  onClick,
  children,
  additionalCssClass,
  ...otherProps
}) => {
  const testId = computeTestId({ testIdParam, captionKey });
  const id = computeId({ idParam, testIdParam, captionKey });
  const caption = computeCaption({ caption: captionParam, captionKey });

  const allergensWithColor = allergens != null && allergens.filter((allergen) => allergen.color != null);
  const displayAllergens = allergensWithColor && allergensWithColor.length > 0;
  const displayHazards = hazardSymbols != null && hazardSymbols.length > 0;
  const displayHazardsAndAllergens = displayHazards || displayAllergens;

  const fireOnClick = uiTrace.traceFunction(onClick, {
    eventName: 'buttonClick',
    componentId: id,
    testId,
    caption,
    showWarningSign,
    completeStatus,
    typeFASIconName,
    hazardSymbols,
    allergens,
    isDanger,
    otherProps,
  });

  return (
    <button
      id={id}
      data-testid={testId}
      data-left-icon={typeFASIconName}
      {...extractTestDataProps(otherProps)}
      className={cx('button is-outlined is-fullwidth complete-btn', { 'is-danger': isDanger }, additionalCssClass)}
      disabled={!!disabled}
      onClick={fireOnClick}
    >
      <div className="full-size-btn">
        <div className="left-btn-side">
          {showWarningSign && (
            <span>
              {/* IMPORTANT: the wrapping "span" needs to be here in case we are clearing showWarningSign so to avoid: DOMException: Failed to execute 'removeChild' on 'Node'*/}
              <i className="fas fa-exclamation-triangle warning-sign" />
            </span>
          )}
          {typeFASIconName && (
            <span>
              {/* IMPORTANT: the wrapping "span" needs to be here in case we are clearing typeFASIconName so to avoid: DOMException: Failed to execute 'removeChild' on 'Node'*/}
              <i key="icon" className={`fas fa-solid ${typeFASIconName}`} />
            </span>
          )}
        </div>
        <div className="caption-btn">
          <div className="rows">
            <div className="row">
              <span>{caption}</span>
            </div>
            {displayHazardsAndAllergens && (
              <div
                className="row hazard-icons-btn"
                style={{
                  minHeight: SYMBOLS_SIZE_PX + 'px',
                  maxHeight: SYMBOLS_SIZE_PX + 'px',
                }}
              >
                <AllergenIcon allergens={allergensWithColor} size={SYMBOLS_SIZE_PX} />
                {hazardSymbols &&
                  hazardSymbols.map((hazardSymbol, symbolIndex) => (
                    <HazardIcon
                      key={symbolIndex}
                      imageId={hazardSymbol.imageId}
                      caption={hazardSymbol.name}
                      size={SYMBOLS_SIZE_PX}
                    />
                  ))}
              </div>
            )}
            {children}
          </div>
        </div>
        <Indicators completeStatus={completeStatus} indicator1={indicator1} indicator2={indicator2} />
      </div>
    </button>
  );
};

ButtonWithIndicator.propTypes = {
  id: PropTypes.string,
  testId: PropTypes.string,
  caption: PropTypes.string,
  captionKey: PropTypes.string,
  showWarningSign: PropTypes.bool,
  typeFASIconName: PropTypes.string,
  hazardSymbols: PropTypes.array,
  allergens: PropTypes.array,
  isDanger: PropTypes.bool,
  completeStatus: PropTypes.string,
  indicator1: PropTypes.string,
  indicator2: PropTypes.string,
  disabled: PropTypes.bool,
  children: PropTypes.node,
  additionalCssClass: PropTypes.string,
  onClick: PropTypes.func.isRequired,
};

export default ButtonWithIndicator;

//
//
//
//
//

const Indicators = ({ completeStatus, indicator1, indicator2 }) => {
  const hasIndicators = completeStatus || indicator1 || indicator2;
  if (!hasIndicators) return null;

  const isJustifyContentInCenter = completeStatus && !indicator1 && !indicator2;

  return (
    <div className={cx('right-btn-side', { 'is-justify-content-center': isJustifyContentInCenter })}>
      <Indicator
        key={`indicator_${indicator1}_${completeStatus}`} // IMPORTANT: force remount because we use font awesome which converts <i> tags to <svg> but they are not updated in case of style changes
        testId="indicator"
        indicator={indicator1}
        completeStatus={completeStatus}
      />
      <Indicator
        key={`indicator_${indicator2}`} // IMPORTANT: force remount because we use font awesome which converts <i> tags to <svg> but they are not updated in case of style changes
        testId="indicator2"
        indicator={indicator2}
      />
    </div>
  );
};
Indicators.propTypes = {
  completeStatus: PropTypes.string,
  indicator1: PropTypes.string,
  indicator2: PropTypes.string,
};

//
//
//
//
//

const Indicator = ({ testId, indicator, completeStatus }) => {
  let className = null;
  if (indicator) {
    switch (indicator) {
      case WorkflowLauncherIndicator.JOB_ALREADY_STARTED:
        className = 'indicator-box fas fa-lock';
        break;
      case WorkflowLauncherIndicator.STOCK_NOT_AVAILABLE:
        className = 'indicator-box indicator-color-red fas fa-warehouse';
        break;
      case WorkflowLauncherIndicator.STOCK_PARTIALLY_AVAILABLE:
        className = 'indicator-box indicator-color-yellow fas fa-warehouse';
        break;
      case WorkflowLauncherIndicator.STOCK_FULLY_AVAILABLE:
        className = 'indicator-box indicator-color-green fas fa-warehouse';
        break;
    }
  } else if (completeStatus) {
    switch (completeStatus) {
      case CompleteStatus.NOT_STARTED:
        className = 'indicator-box indicator-color-red fas fa-circle';
        break;
      case CompleteStatus.IN_PROGRESS:
        className = 'indicator-box indicator-color-yellow fas fa-circle-half-stroke';
        break;
      case CompleteStatus.COMPLETED:
        className = 'indicator-box indicator-color-green fas fa-circle';
        break;
    }
  }

  if (!className) return null;

  // IMPORTANT: Wrap in <span> because FontAwesome mutates the <i> into <svg>; without a stable wrapper React throws removeChild errors.
  return (
    <span>
      <i data-testid={testId} className={className} />
    </span>
  );
};
Indicator.propTypes = {
  testId: PropTypes.string,
  indicator: PropTypes.string,
  completeStatus: PropTypes.string,
};

//
//
//
//
//

const computeCaption = ({ captionKey, caption }) => {
  if (caption) {
    return caption;
  } else if (captionKey) {
    return trl(captionKey);
  } else {
    return '';
  }
};

const extractTestDataProps = (props) => {
  if (!props) return {};
  return Object.keys(props).reduce((acc, key) => {
    if (key.startsWith('data')) {
      acc[key] = props[key];
    }
    return acc;
  }, {});
};
