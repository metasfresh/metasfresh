import React, { useEffect, useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { useStore } from 'react-redux';
import classnames from 'classnames';
import { forEach } from 'lodash';

import { getWorkflowProcess } from '../reducers/wfProcesses';
import { getWorkflowProcessStatus, getWorkflowProcessActivityLine } from '../reducers/wfProcesses_status';

const ViewHeader = () => {
  const location = useLocation();
  const params = useParams();
  const state = useStore().getState();
  const [headerInfo, setHeaderInfo] = useState(null);

  useEffect(() => {
    const { workflowId, activityId, lineId, stepId } = params;

    if (workflowId) {
      const workflow = getWorkflowProcess(state, workflowId);
      const { entries } = workflow.headerProperties;
      const newHeaderInfo = [entries];

      if (activityId && lineId) {
        const workflowStatus = getWorkflowProcessStatus(state, workflowId);
        const line = getWorkflowProcessActivityLine(workflowStatus, activityId, lineId);

        if (line) {
          // this uses hardcoded key
          newHeaderInfo.push([{ caption: 'Caption', value: line.caption }]);
        }

        if (line && stepId) {
          const step = line.steps[stepId];
          const stepInfo = [];

          forEach(step, (k, v) => {
            stepInfo.push({
              caption: v,
              value: k,
            });
          });

          newHeaderInfo.push(stepInfo);
        }
      }

      setHeaderInfo(newHeaderInfo);
    }
  }, [params, location]);

  return (
    <div className={classnames({ 'header-caption': headerInfo })}>
      {headerInfo
        ? headerInfo.map((info, idx) => {
            return (
              <div
                key={idx}
                className={classnames(
                  'picking-step-details centered-text is-size-6',
                  `py-4`,
                  `px-${idx + 4}`,
                  `header_info_${idx}`
                )}
              >
                {info.map(({ caption, value }, i) => {
                  return (
                    <div key={i} className="columns is-mobile is-size-7">
                      <div className="column is-half has-text-left has-text-weight-bold pt-0 pb-0 pl-0 pr-0">
                        {caption}:
                      </div>
                      <div className="column is-half has-text-left pt-0 pb-0">{value}</div>
                    </div>
                    // <p key={i} className="info-line">
                    //   {caption} : {value}
                    // </p>
                  );
                })}
              </div>
            );
          })
        : null}
    </div>
  );
};

export default ViewHeader;
