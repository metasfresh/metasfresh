import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import * as CompleteStatus from '../../constants/CompleteStatus';
import HazardIcon from '../HazardIcon';
import AllergenIcon from '../AllergenIcon';
import * as uiTrace from '../../utils/ui_trace';
import { trl } from '../../utils/translations';
import { computeId, computeTestId } from '../../utils/testing_support';

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
  const indicatorClassName1 = computeIndicatorClassName({ indicator: indicator1, completeStatus });
  const indicatorClassName2 = computeIndicatorClassName({ indicator: indicator2 });
  const hasIndicators = indicatorClassName1 || indicatorClassName2;

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
        {hasIndicators && (
          <div className="right-btn-side">
            <span data-testid={`${testId}-Indicator2`} className={indicatorClassName2} />
            <span data-testid={`${testId}-Indicator`} className={indicatorClassName1} />
          </div>
        )}
      </div>
    </button>
  );
};

const computeIndicatorClassName = ({ indicator, completeStatus }) => {
  if (indicator) {
    let className = `indicator-${indicator}`;
    if (indicator === 'lock') {
      className += ' fas fa-lock';
    }
    return className;
  } else if (completeStatus) {
    switch (completeStatus) {
      case CompleteStatus.NOT_STARTED:
        return 'indicator-red';
      case CompleteStatus.COMPLETED:
        return 'indicator-green';
      case CompleteStatus.IN_PROGRESS:
        return 'indicator-yellow';
      default:
        return '';
    }
  } else {
    return null;
  }
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
  indicator1: PropTypes.string,
  indicator2: PropTypes.string,
  completeStatus: PropTypes.string,
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
