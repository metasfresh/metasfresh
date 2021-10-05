import React, { useEffect, useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { useStore } from 'react-redux';
import classnames from 'classnames';

const ViewHeader = () => {
  const location = useLocation();
  const params = useParams();
  const state = useStore().getState();
  const [headerInfo, setHeaderInfo] = useState(null);

  useEffect(() => {
    const { workflowId } = params;

    if (workflowId) {
      const workflow = state.wfProcesses[workflowId];
      const { entries } = workflow.headerProperties;
      const newHeaderInfo = [entries];

      setHeaderInfo(newHeaderInfo);
    }
  }, [params, location]);

  return (
    <div className="header-caption">
      {headerInfo
        ? headerInfo.map((info, idx) => {
            return (
              <div
                key={idx}
                className={classnames('picking-step-details centered-text is-size-6', `header_info_${idx}`)}
              >
                {info.map(({ caption, value }, i) => {
                  return (
                    <div key={i} className="columns is-mobile">
                      <div className="column is-half has-text-right has-text-weight-bold pt-0 pb-0 pl-0 pr-0">
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
