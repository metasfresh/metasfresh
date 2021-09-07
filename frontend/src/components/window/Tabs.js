import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';
import { last, uniqueId } from 'lodash';

import { activateTab, unselectTab } from '../../actions/WindowActions';
import Tab from './Tab';

const TabSingleEntry = (props) => (
  // eslint-disable-next-line react/prop-types
  <div className="tab-sections">{props.children}</div>
);

class Tabs extends PureComponent {
  constructor(props) {
    super(props);

    const firstTab = props.tabsByIds[props.children[0].key];
    const selected = this.getSelected(firstTab, []);

    this.state = {
      selected,
      tabsVersionId: uniqueId(),
    };
  }

  componentDidMount = () => {
    this.props.dispatch(activateTab('master', last(this.state.selected)));
  };

  componentDidUpdate = (prevProps, prevState) => {
    const { dispatch } = this.props;

    if (prevState.tabsVersionId !== this.state.tabsVersionId) {
      dispatch(activateTab('master', last(this.state.selected)));
    }
  };

  componentWillUnmount() {
    this.props.dispatch(unselectTab('master'));
  }

  getSelected = (tab, selected, reverse) => {
    const { tabsByIds } = this.props;
    selected.push(tab.tabId);

    if (!reverse) {
      if (tab.tabs) {
        selected = this.getSelected(tab.tabs[0], selected);
      }
    } else {
      if (tab.parentTab) {
        selected = this.getSelected(tabsByIds[tab.parentTab], selected, true);
      }
    }

    return selected;
  };

  handleClick = (e, id) => {
    e.preventDefault();

    const firstTab = this.props.tabsByIds[id];
    let reverse = false;

    if (firstTab.parentTab) {
      reverse = true;
    }
    let selected = this.getSelected(firstTab, [], reverse);

    if (firstTab.parentTab) {
      selected = selected.reverse();
    }

    this.setState({
      selected,
      tabsVersionId: uniqueId(),
    });
  };

  handlePillKeyDown = (e, key) => {
    if (e.key === 'Enter') {
      this.handleClick(e, key);
    }
  };

  renderNestedPills = (parentItem, maxWidth, level, nestedPills) => {
    const pillsArray = parentItem.tabs.map((item) => {
      if (item.tabs) {
        this.renderNestedPills(item, maxWidth, level++, nestedPills);
      }

      return this.renderPill(item, maxWidth);
    });

    nestedPills[level] = [
      <ul
        key={`nested-tabs-${parentItem.tabId}`}
        className="nav nav-tabs nested-tabs"
      >
        {pillsArray}
      </ul>,
    ];
  };

  renderPill = (item, maxWidth) => {
    const { tabIndex, modalVisible } = this.props;
    const { selected } = this.state;

    return (
      <li
        id={`tab_${item.internalName}`}
        className="nav-item"
        key={'tab-' + item.tabId}
        onClick={(e) => this.handleClick(e, item.tabId)}
        tabIndex={modalVisible ? -1 : tabIndex}
        onKeyDown={(e) => this.handlePillKeyDown(e, item.tabId)}
        style={{ maxWidth }}
        title={item.description || item.caption}
      >
        <a
          className={classnames('nav-link', {
            active: selected.indexOf(item.tabId) > -1,
          })}
        >
          {item.caption}
        </a>
      </li>
    );
  };

  renderPills = (pills) => {
    const maxWidth = 95 / pills.length + '%';
    const { selected } = this.state;
    const nestedPills = [];

    const pillsArray = pills.map((item) => {
      if (item.tabs && selected.indexOf(item.tabId) > -1) {
        this.renderNestedPills(item, maxWidth, 0, nestedPills);
      }

      return this.renderPill(item, maxWidth);
    });

    return (
      <div className="tabs-wrap">
        <ul className="nav nav-tabs mt-1">{pillsArray}</ul>
        {nestedPills}
      </div>
    );
  };

  renderTabs = (tabs) => {
    const { windowId, onChange } = this.props;
    const { selected } = this.state;

    return tabs.map((item) => {
      if (last(selected) === item.key) {
        const { tabId, queryOnActivate, docId, orderBy, singleRowView } =
          item.props;

        return (
          <div key={'pane-' + item.key} className="tab-pane active">
            <Tab
              {...{
                queryOnActivate,
                tabId,
                docId,
                windowId,
                orderBy,
                singleRowView,
                onChange,
              }}
            >
              {item}
            </Tab>
          </div>
        );
      } else {
        return false;
      }
    });
  };

  setRef = (ref) => {
    this.tabContent = ref;
  };

  render() {
    const { children, fullScreen, tabs } = this.props;

    return (
      <div
        className={classnames('mb-1', {
          'tabs-fullscreen container-fluid': fullScreen,
        })}
      >
        {this.renderPills(tabs)}
        <div className="tab-content" ref={this.setRef}>
          {this.renderTabs(children)}
        </div>
      </div>
    );
  }
}

Tabs.propTypes = {
  tabs: PropTypes.array,
  parentTab: PropTypes.string,
  children: PropTypes.array,
  dispatch: PropTypes.func.isRequired,
  modalVisible: PropTypes.bool.isRequired,
  windowId: PropTypes.string,
  tabsByIds: PropTypes.any,
  onChange: PropTypes.func,
  tabIndex: PropTypes.number,
  fullScreen: PropTypes.any,
};

export default connect((state) => ({
  modalVisible: state.windowHandler.modal.visible,
}))(Tabs);

export { TabSingleEntry };
