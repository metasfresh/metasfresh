import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';

import Table from '../components/table/Table';
import Tabs from '../components/tabs/Tabs';
import MasterWidget from '../components/widget/MasterWidget';
import Dropzone from './Dropzone';
import Separator from './Separator';

class Window extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      fullScreen: null,
      dragActive: false,
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
  }

  toggleTableFullScreen = tabId => {
    this.setState({
      fullScreen: tabId,
    });
  };

  renderTabs = tabs => {
    const { type } = this.props.layout;
    const { data, rowData, newRow, tabsInfo, sort } = this.props;
    const { fullScreen } = this.state;

    if (!Object.keys(data).length) {
      return;
    }

    const dataId = data.ID && data.ID.value;

    return (
      <Tabs
        tabIndex={this.tabIndex.tabs}
        toggleTableFullScreen={this.toggleTableFullScreen}
        fullScreen={fullScreen}
        windowType={type}
      >
        {tabs.map(elem => {
          const {
            tabid,
            caption,
            elements,
            emptyResultText,
            emptyResultHint,
            queryOnActivate,
            supportQuickInput,
            defaultOrderBys,
          } = elem;
          return (
            <Table
              entity="window"
              caption={caption}
              keyProperty="rowId"
              key={tabid}
              rowData={rowData}
              cols={elements}
              tabid={tabid}
              type={type}
              sort={sort}
              orderBy={defaultOrderBys}
              docId={dataId}
              emptyText={emptyResultText}
              emptyHint={emptyResultHint}
              newRow={newRow}
              tabIndex={this.tabIndex.tabs}
              queryOnActivate={queryOnActivate}
              supportQuickInput={supportQuickInput}
              tabInfo={tabsInfo && tabsInfo[tabid]}
              disconnectFromState={true}
            />
          );
        })}
      </Tabs>
    );
  };

  renderSections = sections => {
    return sections.map((elem, id) => {
      const { title, columns } = elem;
      const isFirst = id === 0;
      return (
        <div className="row" key={'section' + id}>
          {title && <Separator {...{ title }} />}
          {columns && this.renderColumns(columns, isFirst)}
        </div>
      );
    });
  };

  renderColumns = (columns, isSectionFirst) => {
    const maxRows = 12;
    const colWidth = Math.floor(maxRows / columns.length);
    return columns.map((elem, id) => {
      const isFirst = id === 0 && isSectionFirst;
      const elementGroups = elem.elementGroups;
      return (
        <div className={'col-sm-' + colWidth} key={'col' + id}>
          {elementGroups && this.renderElementGroups(elementGroups, isFirst)}
        </div>
      );
    });
  };

  renderElementGroups = (group, isFirst) => {
    const { isModal } = this.props;
    return group.map((elem, id) => {
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
            className={
              'panel panel-spaced panel-distance ' +
              (type === 'primary'
                ? 'panel-bordered panel-primary'
                : 'panel-secondary')
            }
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

  renderElements = (elements, tabIndex, isFocused) => {
    const { type } = this.props.layout;
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
          windowType={type}
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
          onBlurWidget={this.handleBlurWidget.bind(this, fieldName)}
          {...elem}
        />
      );
    });
  };

  render() {
    const { sections, tabs } = this.props.layout;
    const { onDropFile, onRejectDropped, onDragStart, isModal } = this.props;

    this.widgets = [];

    return (
      <div key="window" className="window-wrapper">
        <Dropzone
          onDropFile={onDropFile}
          onRejectDropped={onRejectDropped}
          onDragStart={onDragStart}
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
