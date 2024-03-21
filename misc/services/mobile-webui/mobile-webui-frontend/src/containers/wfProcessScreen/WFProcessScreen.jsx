import React, { useEffect } from 'react';
import { useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import { pushHeaderEntry } from '../../actions/HeaderActions';
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

const WFProcessScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId },
  } = useRouteMatch();

  const { iconClassNames: appIconClassName } = useApplicationInfo({ applicationId });

  const { activities, isAllowAbort, headerProperties } = useSelector(
    (state) => getPropsFromState({ state, wfProcessId }),
    shallowEqual
  );

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        values: headerProperties,
        isHomeStop: true,
        homeIconClassName: appIconClassName,
      })
    );
  }, [url, headerProperties, applicationId, wfProcessId]);

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
  }
};

const getPropsFromState = ({ state, wfProcessId }) => {
  const wfProcess = getWfProcess(state, wfProcessId);

  return {
    headerProperties: wfProcess?.headerProperties?.entries ?? [],
    activities: wfProcess ? getActivitiesInOrder(wfProcess) : [],
    isAllowAbort: !!wfProcess?.isAllowAbort,
  };
};

export default WFProcessScreen;
