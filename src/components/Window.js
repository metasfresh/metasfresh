import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import classnames from 'classnames';

import Table from '../components/table/Table';
// import EntryTable from '../components/Table/EntryTable';
import EntryTable from '../components/widget/Entry';
import Tabs, { TabSingleEntry } from '../components/tabs/Tabs';
import MasterWidget from '../components/widget/MasterWidget';
import Dropzone from './Dropzone';
import Separator from './Separator';

class Window extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      fullScreen: null,
      dragActive: false,
      collapsedSections: {},
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

  toggleTableFullScreen = () => {
    this.setState({
      fullScreen: !this.state.fullScreen,
    });
  };

  getTabs = (tabs, dataId, tabsArray, tabsByIds, parentTab) => {
    const { windowId } = this.props.layout;
    const { rowData, newRow, tabsInfo, sort } = this.props;

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
        {...{ tabs, tabsByIds }}
      >
        {tabsArray}
      </Tabs>
    );
  };

  toggleSection = idx => {
    this.setState({
      collapsedSections: {
        ...this.state.collapsedSections,
        [idx]: !this.state.collapsedSections[idx],
      },
    });
  };

  sectionCollapsed = idx => {
    return this.state.collapsedSections[idx];
  };

  renderSections = (sections, dataEntry, extendedData = {}) => {
    return sections.map((elem, idx) => {
      const { title, columns } = elem;
      const isFirst = idx === 0;
      const sectionCollapsed = dataEntry && this.sectionCollapsed(idx);

      return (
        <div key={`section-${idx}`} className="collapsible-section">
          <div className="panel-size-button">
            <button
              className={classnames(
                'btn btn-meta-outline-secondary btn-sm ignore-react-onclickoutside',
                {
                  'meta-icon-show': !sectionCollapsed,
                  'meta-icon-hide': sectionCollapsed,
                }
              )}
              onClick={() => this.toggleSection(idx)}
            />
          </div>
          <div
            className={classnames('row section', {
              collapsed: sectionCollapsed,
            })}
          >
            {title && <Separator {...{ title }} />}
            {columns &&
              this.renderColumns(columns, isFirst, dataEntry, extendedData)}
          </div>
        </div>
      );
    });
  };

  renderColumns = (columns, isSectionFirst, dataEntry, extendedData) => {
    const maxRows = 12;
    const colWidth = Math.floor(maxRows / columns.length);

    return columns.map((elem, id) => {
      const isFirst = id === 0 && isSectionFirst;
      const elementGroups = elem.elementGroups;

      if (dataEntry) {
        return (
          <div className={'col-sm-' + colWidth} key={'col' + id}>
            {this.renderEntryTable(elementGroups, isFirst, extendedData)}
          </div>
        );
      } else {
        return (
          <div className={'col-sm-' + colWidth} key={'col' + id}>
            {elementGroups &&
              this.renderElementGroups(elementGroups, isFirst, extendedData)}
          </div>
        );
      }
    });
  };

  addRefToWidgets = c => {
    if (c) {
      this.widgets.push(c);
    }
  };

  renderEntryTable = (groups, isFirst, extendedData) => {
    const rows = groups.reduce((rowsArray, group) => {
      rowsArray.push(group.elementsLine[0].elements);

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
            isFirst,
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

  render() {
    const { sections, tabs } = this.props.layout;
    const {
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

export default Window;
