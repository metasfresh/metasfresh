import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { connect } from 'react-redux';

import keymap from '../../shortcuts/keymap';
import DocumentList from '../../containers/DocumentList';
import Tooltips from '../tooltips/Tooltips';
import Attachments from './Attachments';
import DocumentReferences from '../documentReferences/DocumentReferences';

/**
 * @file Class based component.
 * @module SideList
 * @extends Component
 */
class SideList extends Component {
  constructor(props) {
    super(props);

    const { defaultTab } = this.props;

    this.state = {
      tab: defaultTab ? defaultTab : 0,
      tooltipOpen: false,
    };
  }

  /**
   * @method UNSAFE_componentWillReceiveProps
   * @summary ToDo: Describe the method.
   * @param {objec} props
   */
  UNSAFE_componentWillReceiveProps(props) {
    const { defaultTab } = props;

    if (defaultTab !== this.props.defaultTab) {
      this.setState({
        tab: defaultTab,
      });
    }
  }

  /**
   * @method handleClickOutside
   * @summary ToDo: Describe the method.
   */
  handleClickOutside = () => {
    const { closeSideList } = this.props;
    closeSideList();
  };

  /**
   * @method changeTab
   * @summary ToDo: Describe the method.
   * @param {*} index
   */
  changeTab = (index) => {
    this.setState({
      tab: index,
    });
  };

  /**
   * @method renderBody
   * @summary ToDo: Describe the method.
   */
  renderBody = () => {
    const {
      windowId,
      closeOverlays,
      closeSideList,
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
              viewId && viewId.windowType === windowId ? viewId.id : null
            }
            defaultSort={
              sorting && sorting.windowType === windowId ? sorting.sort : null
            }
            defaultPage={
              pagination && pagination.windowType === windowId
                ? pagination.page
                : null
            }
            autofocus={true}
            {...{
              windowId,
              closeOverlays,
              closeSideList,
              isSideListShow,
            }}
          />
        );
      case 1:
        return <DocumentReferences windowId={windowId} documentId={docId} />;
      case 2:
        return <Attachments {...{ windowType: windowId, docId }} />;
    }
  };

  /**
   * @method toggleTooltip
   * @summary ToDo: Describe the method.
   * @param {*} id
   */
  toggleTooltip = (id) => {
    this.setState({
      tooltipOpen: id,
    });
  };

  /**
   * @method render
   * @summary ToDo: Describe the method.
   */
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
        ref={(c) => (this.panel = c)}
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

/**
 * @typedef {object} Props Component props
 * @prop {*} closeOverlays
 * @prop {*} closeSideList
 * @prop {*} defaultTab
 * @prop {*} docId
 * @prop {*} isSideListShow
 * @prop {*} pagination
 * @prop {*} sorting
 * @prop {*} viewId
 * @prop {*} windowType
 */
SideList.propTypes = {
  closeOverlays: PropTypes.any,
  closeSideList: PropTypes.any,
  defaultTab: PropTypes.any,
  docId: PropTypes.any,
  isSideListShow: PropTypes.any,
  pagination: PropTypes.any,
  sorting: PropTypes.any,
  viewId: PropTypes.any,
  windowId: PropTypes.any,
};

/**
 * @method mapStateToProps
 * @summary ToDo: Describe the method.
 * @param {object} state
 */
const mapStateToProps = (state) => {
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
};

export default connect(mapStateToProps)(onClickOutside(SideList));
