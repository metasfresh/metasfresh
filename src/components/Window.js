import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import classnames from 'classnames';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';

import Table from './table/Table';
import EntryTable from './table/EntryTable';
import TableContextShortcuts from './keyshortcuts/TableContextShortcuts';
import keymap from '../shortcuts/keymap';
import Tabs, { TabSingleEntry } from './tabs/Tabs';
import Tooltips from './tooltips/Tooltips';
import MasterWidget from './widget/MasterWidget';
import Dropzone from './Dropzone';
import Separator from './Separator';
import { INITIALLY_OPEN, INITIALLY_CLOSED } from '../constants/Constants';

/**
 * @file Class based component.
 * @module Window
 * @extends Component
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
      this.tabIndex = {
        firstColumn: 0,
        secondColumn: 0,
        tabs: 0,
      };
    } else {
      this.tabIndex = {
        firstColumn: 1,
        secondColumn: 2,
        tabs: 3,
      };
    }

    this.toggleTableFullScreen = this.toggleTableFullScreen.bind(this);
    this.handleBlurWidget = this.handleBlurWidget.bind(this);
  }

  componentMountUpdate() {
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
      const tabObject = tabs.find(tab => {
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
   * @method toggleSection
   * @summary ToDo: Describe the method.
   * @param {*} idx
   * @param {*} tabId
   */
  toggleSection = (idx, tabId = '') => {
    this.setState({
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
  sectionCollapsed = (idx, tabId = '') => {
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
    const { windowId } = this.props.layout;
    const { rowData, newRow, tabsInfo, sort, allowShortcut } = this.props;
    const { fullScreen, isSectionExpandTooltipShow } = this.state;

    tabs.forEach(elem => {
      const {
        tabId,
        caption,
        description,
        elements,
        sections,
        internalName,
        emptyResultText,
        emptyResultHint,
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

      const dataEntry = singleRowDetailView || false;

      if (dataEntry) {
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
              rowData,
              tabId,
              windowId,
              sort,
              newRow,
              internalName,
            }}
          >
            {sections && this.renderSections(sections, dataEntry, { tabId })}
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
              rowData,
              tabId,
              windowId,
              sort,
              newRow,
              internalName,
            }}
            entity="window"
            keyProperty="rowId"
            key={tabId}
            cols={elements}
            orderBy={defaultOrderBys}
            docId={dataId}
            emptyText={emptyResultText}
            emptyHint={emptyResultHint}
            tabIndex={this.tabIndex.tabs}
            queryOnActivate={queryOnActivate}
            supportQuickInput={supportQuickInput}
            tabInfo={tabsInfo && tabsInfo[tabId]}
            disconnectFromState={true}
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
  renderTabs = tabs => {
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
        toggleTableFullScreen={this.toggleTableFullScreen}
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
   * @param {*} dataEntry
   * @param {*} extendedData
   */
  renderSections = (sections, dataEntry, extendedData = {}) => {
    return sections.map((elem, idx) => {
      const { title, columns, closableMode } = elem;
      const isFirst = idx === 0;
      const sectionCollapsed =
        dataEntry && this.sectionCollapsed(idx, extendedData.tabId);
      const collapsible =
        closableMode === INITIALLY_OPEN || closableMode === INITIALLY_CLOSED;

      return (
        <div
          key={`section-${idx}`}
          className={classnames('section', { collapsed: sectionCollapsed })}
        >
          {title && (
            <Separator
              {...{ title, idx, sectionCollapsed, collapsible }}
              tabId={extendedData.tabId}
              onClick={this.toggleSection}
            />
          )}
          <div
            className={classnames('row', {
              'collapsible-section': collapsible,
              collapsed: sectionCollapsed,
            })}
          >
            {columns &&
              this.renderColumns(columns, isFirst, dataEntry, extendedData)}
          </div>
        </div>
      );
    });
  };

  /**
   * @method renderColumns
   * @summary ToDo: Describe the method.
   * @param {*} columns
   * @param {*} isSectionFirst
   * @param {*} dataEntry
   * @param {*} extendedData
   */
  renderColumns = (columns, isSectionFirst, dataEntry, extendedData) => {
    const maxRows = 12;
    const colWidth = Math.floor(maxRows / columns.length);

    return columns.map((elem, id) => {
      const isFirst = id === 0 && isSectionFirst;
      const elementGroups = elem.elementGroups;

      if (dataEntry) {
        return (
          <div className="col-sm-12" key={`col-${id}`}>
            {this.renderEntryTable(elementGroups, extendedData)}
          </div>
        );
      } else {
        return (
          <div className={`col-sm-${colWidth}`} key={`col-${id}`}>
            {elementGroups &&
              this.renderElementGroups(elementGroups, isFirst, extendedData)}
          </div>
        );
      }
    });
  };

  /**
   * @method toggleSection
   * @summary ToDo: Describe the method.
   * @param {*} c
   */
  addRefToWidgets = c => {
    if (c) {
      this.widgets.push(c);
    }
  };

  /**
   * @method renderEntryTable
   * @summary ToDo: Describe the method.
   * @param {*} idx
   * @param {*} tabId
   */
  renderEntryTable = (groups, extendedData) => {
    const rows = groups.reduce((rowsArray, group) => {
      const cols = [];
      group.elementsLine.forEach(line => {
        if (line && line.elements && line.elements.length) {
          cols.push(line.elements[0]);
        }
      });

      rowsArray.push({
        cols,
        colsCount: group.columnCount,
      });

      return rowsArray;
    }, []);
    const rowData = this.props.rowData.get(extendedData.tabId);
    const { fullScreen } = this.state;

    return (
      <div
        className={classnames(
          'panel panel-primary panel-bordered',
          'panel-bordered-force table-flex-wrapper',
          'document-list-table js-not-unselect'
        )}
      >
        <EntryTable
          {...{
            ...this.props,
            rows,
            rowData,
            extendedData,
            fullScreen,
          }}
          addRefToWidgets={this.addRefToWidgets}
          handleBlurWidget={this.handleBlurWidget}
        />
      </div>
    );
  };

  /**
   * @method renderElementGroups
   * @summary ToDo: Describe the method.
   * @param {*} groups
   * @param {*} isFirst
   */
  renderElementGroups = (groups, isFirst) => {
    const { isModal } = this.props;

    return groups.map((elem, id) => {
      const { type, elementsLine } = elem;
      const shouldBeFocused = isFirst && id === 0;
      const tabIndex =
        type === 'primary'
          ? this.tabIndex.firstColumn
          : this.tabIndex.secondColumn;

      return (
        elementsLine &&
        elementsLine.length > 0 && (
          <div
            key={'elemGroups' + id}
            ref={c => {
              if (this.focused) return;
              if (isModal && shouldBeFocused && c) c.focus();
              this.focused = true;
            }}
            className={classnames('panel panel-spaced panel-distance', {
              'panel-bordered panel-primary': type === 'primary',
              'panel-secondary': type !== 'primary',
            })}
          >
            {this.renderElementsLine(elementsLine, tabIndex, shouldBeFocused)}
          </div>
        )
      );
    });
  };

  /**
   * @method renderElementsLine
   * @summary ToDo: Describe the method.
   * @param {*} elementsLine
   * @param {*} tabIndex
   * @param {*} shouldBeFocused
   */
  renderElementsLine = (elementsLine, tabIndex, shouldBeFocused) => {
    return elementsLine.map((elem, id) => {
      const { elements } = elem;
      const isFocused = shouldBeFocused && id === 0;
      return (
        elements &&
        elements.length > 0 && (
          <div className="elements-line" key={'line' + id}>
            {this.renderElements(elements, tabIndex, isFocused)}
          </div>
        )
      );
    });
  };

  /**
   * @method renderElements
   * @summary ToDo: Describe the method.
   * @param {*} elements
   * @param {*} tabIndex
   * @param {*} isFocused
   */
  renderElements = (elements, tabIndex, isFocused) => {
    const { windowId } = this.props.layout;
    const { data, modal, tabId, rowId, dataId, isAdvanced } = this.props;
    const { fullScreen } = this.state;

    return elements.map((elem, id) => {
      const autoFocus = isFocused && id === 0;
      const widgetData = elem.fields.map(item => data[item.field] || -1);
      const fieldName = elem.fields ? elem.fields[0].field : '';
      const relativeDocId = data.ID && data.ID.value;

      return (
        <MasterWidget
          ref={c => {
            if (c) {
              this.widgets.push(c);
            }
          }}
          entity="window"
          key={'element' + id}
          windowType={windowId}
          dataId={dataId}
          widgetData={widgetData}
          isModal={!!modal}
          tabId={tabId}
          rowId={rowId}
          relativeDocId={relativeDocId}
          isAdvanced={isAdvanced}
          tabIndex={tabIndex}
          autoFocus={!modal && autoFocus}
          fullScreen={fullScreen}
          fieldName={fieldName}
          onBlurWidget={this.handleBlurWidget}
          {...elem}
        />
      );
    });
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

    this.widgets = [];

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
  modal: PropTypes.object,
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
