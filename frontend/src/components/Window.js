import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';

import Table from '../containers/Table';
import TableContextShortcuts from './keyshortcuts/TableContextShortcuts';
import keymap from '../shortcuts/keymap';
import Tabs, { TabSingleEntry } from './window/Tabs';
import Tooltips from './tooltips/Tooltips';
import Section from './window/Section';
import Dropzone from './Dropzone';
import { INITIALLY_CLOSED } from '../constants/Constants';

/**
 * @file Class based component.
 * @module Window
 * @extends PureComponent
 */
class Window extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      fullScreen: null,
      dragActive: false,
      collapsedSections: {},
      isSectionExpandTooltipShow: false,
    };

    if (props.isModal) {
      this.tabIndex = { tabs: 0 };
    } else {
      this.tabIndex = { tabs: 3 };
    }

    this.widgets = [];

    this.toggleTableFullScreen = this.toggleTableFullScreen.bind(this);
    this.handleBlurWidget = this.handleBlurWidget.bind(this);
    this.requestElementGroupFocus = this.requestElementGroupFocus.bind(this);
  }

  componentDidMount() {
    this._setInitialSectionsState();
  }

  /**
   * @method _setInitialSectionsState
   * @summary ToDo: Describe the method.
   */
  _setInitialSectionsState = () => {
    const sections = this._getInitialSectionsState();

    this.setState({
      collapsedSections: sections,
    });
  };

  /**
   * @method _getInitialSectionsState
   * @summary ToDo: Describe the method.
   */
  _getInitialSectionsState = () => {
    const { tabs, activeTab } = this.props.layout;

    if (tabs) {
      const tabObject = tabs.find((tab) => {
        let ret = false;
        if (tab.tabId === activeTab) {
          ret = true;
        }
        if (tab.tabs) {
          for (let i = 0; i < tab.tabs.length; i += 1) {
            if (tab.tabs[i].tabId === activeTab) {
              ret = true;
              break;
            }
          }
        }

        return ret;
      });

      if (tabObject && tabObject.tabs && tabObject.tabs.length) {
        return tabObject.tabs.reduce((sectionsObject, tab) => {
          if (tab.sections) {
            tab.sections.forEach((sec, idx) => {
              if (sec.closableMode === INITIALLY_CLOSED) {
                sectionsObject[`${tab.tabId}_${idx}`] = true;
              }
            });
          }

          return sectionsObject;
        }, {});
      }
    }

    return {};
  };

  /**
   * @method toggleTableFullScreen
   * @summary ToDo: Describe the method.
   */
  toggleTableFullScreen = () => {
    this.setState({
      fullScreen: !this.state.fullScreen,
    });
  };

  /**
   * @method toggleSectionCollapsed
   * @summary ToDo: Describe the method.
   * @param {*} idx
   * @param {*} tabId
   */
  toggleSectionCollapsed = (idx, tabId = '') => {
    this.setState({
      ...this.state,
      collapsedSections: {
        ...this.state.collapsedSections,
        [`${tabId}_${idx}`]: !this.state.collapsedSections[`${tabId}_${idx}`],
      },
    });
  };

  /**
   * @method sectionCollapsed
   * @summary ToDo: Describe the method.
   * @param {*} idx
   * @param {*} tabId
   */
  isSectionCollapsed = (idx, tabId = '') => {
    return this.state.collapsedSections[`${tabId}_${idx}`];
  };

  /**
   * @method hideSectionExpandTooltip
   * @summary ToDo: Describe the method.
   * @param {*} key
   */
  hideSectionExpandTooltip = (key = null) => {
    this.setState({
      isSectionExpandTooltipShow: key,
    });
  };

  /**
   * @method showSectionExpandTooltip
   * @summary ToDo: Describe the method.
   */
  showSectionExpandTooltip = () => {
    this.setState({
      isSectionExpandTooltipShow: keymap.TOGGLE_EXPAND,
    });
  };

  /**
   * @method getTabs
   * @summary ToDo: Describe the method.
   * @param {*} tabs
   * @param {*} dataId
   * @param {*} tabsArray
   * @param {*} tabsByIds
   * @param {*} parentTab
   */
  getTabs = (tabs, dataId, tabsArray, tabsByIds, parentTab) => {
    const {
      layout: { windowId },
      newRow,
      tabsInfo,
      sort,
      allowShortcut,
    } = this.props;
    const { fullScreen, isSectionExpandTooltipShow } = this.state;

    tabs.forEach((elem) => {
      const {
        tabId,
        caption,
        description,
        sections,
        internalName,
        queryOnActivate,
        supportQuickInput,
        defaultOrderBys,
        singleRowDetailView,
      } = elem;
      elem.tabIndex = this.tabIndex.tabs;
      if (parentTab) {
        elem.parentTab = parentTab;
      }

      tabsByIds[elem.tabId] = elem;

      const isDataEntry = singleRowDetailView || false;

      if (isDataEntry) {
        tabsArray.push(
          <TabSingleEntry
            docId={dataId}
            key={tabId}
            queryOnActivate={queryOnActivate}
            singleRowView={true}
            tabIndex={this.tabIndex.tabs}
            {...{
              caption,
              description,
              tabId,
              windowId,
              sort,
              newRow,
              internalName,
            }}
          >
            {sections && this.renderSections(sections, isDataEntry, { tabId })}
            <button
              className="btn-icon btn-meta-outline-secondary pointer btn-fullscreen"
              onClick={this.toggleTableFullScreen}
              onMouseEnter={this.showSectionExpandTooltip}
              onMouseLeave={this.hideSectionExpandTooltip}
              tabIndex="-1"
            >
              {fullScreen ? (
                <i className="meta-icon-collapse" />
              ) : (
                <i className="meta-icon-fullscreen" />
              )}

              {isSectionExpandTooltipShow === keymap.TOGGLE_EXPAND && (
                <Tooltips
                  name={keymap.TOGGLE_EXPAND}
                  action={
                    fullScreen
                      ? counterpart.translate('window.table.collapse')
                      : counterpart.translate('window.table.expand')
                  }
                  type={''}
                />
              )}
            </button>
            {allowShortcut && (
              <TableContextShortcuts
                handleToggleExpand={this.toggleTableFullScreen}
              />
            )}
          </TabSingleEntry>
        );
      } else {
        tabsArray.push(
          <Table
            {...{
              caption,
              description,
              tabId,
              windowId,
              sort,
              newRow,
              internalName,
            }}
            toggleFullScreen={this.toggleTableFullScreen}
            entity="window"
            key={tabId}
            orderBy={defaultOrderBys}
            docId={dataId}
            tabIndex={this.tabIndex.tabs}
            queryOnActivate={queryOnActivate}
            supportQuickInput={supportQuickInput}
            tabInfo={tabsInfo && tabsInfo[tabId]}
          />
        );
      }

      if (elem.tabs) {
        this.getTabs(elem.tabs, dataId, tabsArray, tabsByIds, tabId);
      }
    });
  };

  /**
   * @method renderTabs
   * @summary ToDo: Describe the method.
   * @param {*} tabs
   */
  renderTabs = (tabs) => {
    const {
      layout: { windowId },
      data,
      dataId,
    } = this.props;
    const { fullScreen } = this.state;
    const tabsArray = [];
    const tabsByIds = {};

    if (!Object.keys(data).length) {
      return;
    }

    this.getTabs(tabs, dataId, tabsArray, tabsByIds, null);

    return (
      <Tabs
        tabIndex={this.tabIndex.tabs}
        fullScreen={fullScreen}
        windowId={windowId}
        onChange={this._setInitialSectionsState}
        {...{ tabs, tabsByIds }}
      >
        {tabsArray}
      </Tabs>
    );
  };

  /**
   * @method renderSections
   * @summary ToDo: Describe the method.
   * @param {*} sections
   * @param {*} isDataEntry
   * @param {*} extendedData
   */
  renderSections = (sections, isDataEntry, extendedData = {}) => {
    const {
      layout: { windowId },
      tabId,
      rowId,
      dataId,
      data,
      isModal,
      isAdvanced,
    } = this.props;
    const { fullScreen } = this.state;

    return sections.map((sectionLayout, sectionIndex) => {
      const isSectionCollapsed =
        isDataEntry &&
        this.isSectionCollapsed(sectionIndex, extendedData.tabId);

      return (
        <Section
          key={`section-${sectionIndex}`}
          //
          sectionLayout={sectionLayout}
          sectionIndex={sectionIndex}
          //
          windowId={windowId}
          tabId={tabId || extendedData.tabId}
          rowId={rowId}
          dataId={dataId}
          //
          data={data}
          isDataEntry={isDataEntry}
          extendedData={extendedData}
          //
          isModal={isModal}
          isAdvanced={isAdvanced}
          isFullScreen={fullScreen}
          //
          onBlurWidget={this.handleBlurWidget}
          addRefToWidgets={this.addRefToWidgets}
          requestElementGroupFocus={this.requestElementGroupFocus}
          //
          isSectionCollapsed={isSectionCollapsed}
          toggleSectionCollapsed={this.toggleSectionCollapsed}
        />
      );
    });
  };

  /**
   * @method addRefToWidgets
   * @summary ToDo: Describe the method.
   * @param {*} c
   */
  addRefToWidgets = (c) => {
    if (c) {
      this.widgets.push(c);
    }
  };

  /**
   * @method handleBlurWidget
   * @summary ToDo: Describe the method.
   * @param {*} fieldName
   */
  handleBlurWidget(fieldName) {
    let currentWidgetIndex = -1;

    if (this.widgets) {
      this.widgets.forEach((widget, index) => {
        if (widget && widget.props && widget.props.widgetData) {
          let widgetData = widget.props.widgetData[0];
          if (widgetData && widgetData.field === fieldName) {
            currentWidgetIndex = index;
          }
        }
      });

      if (currentWidgetIndex >= 0) {
        let nextWidgetIndex = Math.min(
          this.widgets.length - 1,
          currentWidgetIndex + 1
        );

        // eslint-disable-next-line react/no-find-dom-node
        let element = ReactDOM.findDOMNode(this.widgets[nextWidgetIndex]);
        if (element) {
          let tabElement = element.querySelector('[tabindex]');

          if (tabElement) {
            tabElement.focus();
          }
        }
      }
    }
  }

  requestElementGroupFocus(elementGroupComponent) {
    if (!this.elementGroupFocused) {
      elementGroupComponent.focus();
      this.elementGroupFocused = true;
    }
  }

  /**
   * @method render
   * @summary ToDo: Describe the method.
   */
  render() {
    const {
      layout: { sections, tabs },
      handleDropFile,
      handleRejectDropped,
      handleDragStart,
      isModal,
    } = this.props;

    this.elementGroupFocused = false;

    return (
      <div key="window" className="window-wrapper">
        <Dropzone
          handleDropFile={handleDropFile}
          handleRejectDropped={handleRejectDropped}
          handleDragStart={handleDragStart}
        >
          <div className="sections-wrapper">
            {sections && this.renderSections(sections)}
          </div>
        </Dropzone>
        {!isModal && (
          <div className="mt-1 tabs-wrapper">
            {tabs && this.renderTabs(tabs)}
          </div>
        )}
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {shape} layout
 * @prop {func} handleDropFile
 * @prop {func} handleRejectDropped
 * @prop {func} handleDragStart
 * @prop {bool} isModal
 * @prop {shape} rowData
 * @prop {bool} newRow
 * @prop {shape} tabsInfo
 * @prop {func} sort
 * @prop {bool} allowShortcut
 * @prop {shape|array} data
 * @prop {string} dataId
 * @prop {object} modal
 * @prop {string} tabId
 * @prop {string} rowId
 * @prop {bool} isAdvanced
 */
Window.propTypes = {
  layout: PropTypes.shape(),
  handleDropFile: PropTypes.func,
  handleRejectDropped: PropTypes.func,
  handleDragStart: PropTypes.func,
  isModal: PropTypes.bool,
  rowData: PropTypes.shape(),
  newRow: PropTypes.bool,
  tabsInfo: PropTypes.shape(),
  sort: PropTypes.func,
  allowShortcut: PropTypes.bool,
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  dataId: PropTypes.string,
  modal: PropTypes.bool,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  isAdvanced: PropTypes.bool,
};

Window.defaultProps = {
  handleDropFile: () => {
    // eslint-disable-next-line no-console
    console.warn('TODO: handleDropFile prop is missing');
  },
  handleRejectDropped: () => {
    // eslint-disable-next-line no-console
    console.warn('TODO: handleRejectDropped prop is missing');
  },
  handleDragStart: () => {
    // eslint-disable-next-line no-console
    console.warn('TODO: handleDragStart prop is missing');
  },
};

export default Window;
