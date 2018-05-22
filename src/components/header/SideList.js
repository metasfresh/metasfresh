import counterpart from 'counterpart';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { connect } from 'react-redux';

import keymap from '../../shortcuts/keymap';
import DocumentList from '../app/DocumentList';
import Tooltips from '../tooltips/Tooltips';
import Attachments from './Attachments';
import Referenced from './Referenced';

class SideList extends Component {
  constructor(props) {
    super(props);

    const { defaultTab } = this.props;

    this.state = {
      tab: defaultTab ? defaultTab : 0,
      tooltipOpen: false,
    };
  }

  handleClickOutside = () => {
    const { onClose } = this.props;
    onClose();
  };

  changeTab = index => {
    this.setState({
      tab: index,
    });
  };

  renderBody = () => {
    const {
      windowType,
      onCloseOverlays,
      onClose,
      isSideListShow,
      docId,
      pagination,
      sorting,
      viewId,
    } = this.props;

    const { tab } = this.state;

    switch (tab) {
      case 0:
        return (
          <DocumentList
            type="list"
            readonly={true}
            defaultViewId={
              viewId && viewId.windowType === windowType ? viewId.id : null
            }
            defaultSort={
              sorting && sorting.windowType === windowType ? sorting.sort : null
            }
            defaultPage={
              pagination && pagination.windowType === windowType
                ? pagination.page
                : null
            }
            selected={[docId]}
            disconnectFromState={true}
            autofocus={true}
            onCloseSideList={onClose}
            onCloseOverlays={onCloseOverlays}
            {...{
              windowType,
              isSideListShow,
            }}
          />
        );
      case 1:
        return <Referenced {...{ windowType, docId }} />;
      case 2:
        return <Attachments {...{ windowType, docId }} />;
    }
  };

  toggleTooltip = id => {
    this.setState({
      tooltipOpen: id,
    });
  };

  render() {
    const { tab, tooltipOpen } = this.state;

    const tabs = [
      {
        icon: 'meta-icon-list',
        title: counterpart.translate(
          'mainScreen.sideList.documentList.tooltip'
        ),
      },
      {
        icon: 'meta-icon-share',
        title: counterpart.translate(
          'mainScreen.sideList.referencedDocuments.tooltip'
        ),
      },
      {
        icon: 'meta-icon-attachments',
        title: counterpart.translate('mainScreen.sideList.attachments.tooltip'),
      },
    ];

    return (
      <div
        ref={c => (this.panel = c)}
        className="order-list-panel overlay-shadow order-list-panel-open js-not-unselect"
      >
        <div className="order-list-panel-body">
          <div className="order-list-nav">
            {tabs.map((item, index) => (
              <div
                key={index}
                className={
                  'order-list-btn tooltip-parent ' +
                  (index === tab ? 'active ' : '')
                }
                onClick={() => this.changeTab(index)}
                onMouseEnter={() => this.toggleTooltip(index)}
                onMouseLeave={() => this.toggleTooltip()}
                tabIndex={0}
              >
                <i className={item.icon} />
                {tooltipOpen === index && (
                  <Tooltips
                    name={keymap['OPEN_SIDEBAR_MENU_' + index]}
                    action={item.title}
                    type={''}
                  />
                )}
              </div>
            ))}
          </div>
          <div className="order-list-body">{this.renderBody()}</div>
        </div>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { listHandler } = state;
  const { sorting, pagination, viewId } = listHandler || {
    sorting: {},
    pagination: {},
    viewId: {},
  };

  return {
    sorting,
    pagination,
    viewId,
  };
}

export default connect(mapStateToProps)(onClickOutside(SideList));
