import React, { useEffect } from 'react';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import { updateHeaderEntry } from '../../actions/HeaderActions';
import { getActivitiesInOrder, getWfProcess } from '../../reducers/wfProcesses';

import AbortButton from './AbortButton';

import ScanActivity, { COMPONENTTYPE_ScanBarcode } from '../activities/scan/ScanActivity';
import PickProductsActivity, { COMPONENTTYPE_PickProducts } from '../activities/picking/PickProductsActivity';
import ConfirmActivity from '../activities/confirmButton/ConfirmActivity';
import GenerateHUQRCodesActivity from '../activities/manufacturing/generateHUQRCodes/GenerateHUQRCodesActivity';
import RawMaterialIssueActivity from '../activities/manufacturing/issue/RawMaterialIssueActivity';
import IssueAdjustmentActivity from '../activities/manufacturing/issue_adjustment/IssueAdjustmentActivity';
import MaterialReceiptActivity from '../activities/manufacturing/receipt/MaterialReceiptActivity';
import DistributionMoveActivity from '../activities/distribution/DistributionMoveActivity';
import { useApplicationInfo } from '../../reducers/applications';
import ScanAndValidateActivity, {
  COMPONENTTYPE_ScanAndValidateBarcode,
} from '../activities/scan/ScanAndValidateActivity';
import { useScreenDefinition } from '../../hooks/useScreenDefinition';
import { appLaunchersLocation } from '../../routes/launchers';
import { useMobileLocation } from '../../hooks/useMobileLocation';

const WFProcessScreen = () => {
  const { wfProcessId } = useMobileLocation();
  const { parentUrl, activities, isAllowAbort, headerProperties } = useSelector(
    (state) => getPropsFromState({ state, wfProcessId }),
    shallowEqual
  );

  const { url, applicationId } = useScreenDefinition({
    screenId: 'WFProcessScreen',
    back: parentUrl ? parentUrl : appLaunchersLocation,
    isHomeStop: true,
  });

  const { iconClassNames: appIconClassName, caption: appCaption } = useApplicationInfo({ applicationId });

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      updateHeaderEntry({
        location: url,
        caption: appCaption,
        values: headerProperties,
        homeIconClassName: appIconClassName,
      })
    );
  }, [url, headerProperties, appIconClassName]);

  return (
    <div className="section pt-2">
      <div className="container">
        {activities.length > 0 &&
          activities.map((activityItem, index) => {
            return renderActivityComponent({
              applicationId,
              wfProcessId,
              activityItem,
              isLastActivity: index === activities.length - 1,
            });
          })}
        {isAllowAbort ? <AbortButton applicationId={applicationId} wfProcessId={wfProcessId} /> : null}
      </div>
    </div>
  );
};

const renderActivityComponent = ({ applicationId, wfProcessId, activityItem, isLastActivity }) => {
  switch (activityItem.componentType) {
    case COMPONENTTYPE_ScanBarcode:
      return (
        <ScanActivity
          key={activityItem.activityId}
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityState={activityItem}
        />
      );
    case COMPONENTTYPE_PickProducts:
      return (
        <PickProductsActivity
          key={activityItem.activityId}
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityId={activityItem.activityId}
          activity={activityItem}
        />
      );
    case 'common/confirmButton':
      return (
        <ConfirmActivity
          key={activityItem.activityId}
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityId={activityItem.activityId}
          caption={activityItem.caption}
          promptQuestion={activityItem.componentProps.question}
          userInstructions={activityItem.userInstructions}
          isUserEditable={activityItem.dataStored.isUserEditable}
          isProcessing={activityItem.dataStored.processing}
          completeStatus={activityItem.dataStored.completeStatus}
          isLastActivity={isLastActivity}
        />
      );
    case 'manufacturing/rawMaterialsIssue':
      return (
        <RawMaterialIssueActivity
          key={activityItem.activityId}
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityId={activityItem.activityId}
          activityState={activityItem}
        />
      );
    case 'manufacturing/rawMaterialsIssueAdjust':
      return (
        <IssueAdjustmentActivity
          key={activityItem.activityId}
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityId={activityItem.activityId}
          activityState={activityItem}
        />
      );
    case 'manufacturing/materialReceipt':
      return (
        <MaterialReceiptActivity
          key={activityItem.activityId}
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityId={activityItem.activityId}
          activityState={activityItem}
        />
      );
    case 'manufacturing/generateHUQRCodes':
      return (
        <GenerateHUQRCodesActivity
          key={activityItem.activityId}
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityState={activityItem}
        />
      );
    case 'distribution/move':
      return (
        <DistributionMoveActivity
          key={activityItem.activityId}
          id={activityItem.activityId}
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityId={activityItem.activityId}
          activityState={activityItem}
        />
      );
    case COMPONENTTYPE_ScanAndValidateBarcode:
      return (
        <ScanAndValidateActivity
          key={activityItem.activityId}
          applicationId={applicationId}
          wfProcessId={wfProcessId}
          activityState={activityItem}
        />
      );
  }
};

const getPropsFromState = ({ state, wfProcessId }) => {
  const wfProcess = getWfProcess(state, wfProcessId);

  return {
    parentUrl: wfProcess?.parent?.url,
    headerProperties: wfProcess?.headerProperties?.entries ?? [],
    activities: wfProcess ? getActivitiesInOrder(wfProcess) : [],
    isAllowAbort: !!wfProcess?.isAllowAbort,
  };
};

export default WFProcessScreen;
