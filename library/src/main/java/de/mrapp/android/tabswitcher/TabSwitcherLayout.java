/*
 * Copyright 2016 - 2017 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.tabswitcher;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.NoSuchElementException;

import de.mrapp.android.tabswitcher.model.Layout;

/**
 * Defines the interface, a layout, which implements the functionality of a {@link TabSwitcher},
 * must implement.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public interface TabSwitcherLayout extends Iterable<Tab> {

    /**
     * Sets the decorator, which allows to inflate the views, which correspond to the tabs of the
     * tab switcher.
     *
     * @param decorator
     *         The decorator, which should be set, as an instance of the class {@link
     *         TabSwitcherDecorator}. The decorator may not be null
     */
    void setDecorator(@NonNull TabSwitcherDecorator decorator);

    /**
     * Returns the decorator, which allows to inflate the views, which correspond to the tabs of the
     * tab switcher.
     *
     * @return The decorator as an instance of the class {@link TabSwitcherDecorator} or null, if no
     * decorator has been set
     */
    TabSwitcherDecorator getDecorator();

    /**
     * Adds a listener, which should be notified about the tab switcher's events.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         TabSwitcherListener}. The listener may not be null
     */
    void addListener(@NonNull TabSwitcherListener listener);

    /**
     * Removes a specific listener, which should not be notified about the tab switcher's events,
     * anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         TabSwitcherListener}. The listener may not be null
     */
    void removeListener(@NonNull TabSwitcherListener listener);

    /**
     * Adds a new listener, which should be notified, when a tab is about to be closed by clicking
     * its close button.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         TabCloseListener}. The listener may not be null
     */
    void addCloseTabListener(@NonNull TabCloseListener listener);

    /**
     * Removes a specific listener, which should not be notified, when a tab is about to be closed
     * by clicking its close button, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         TabCloseListener}. The listener may not be null
     */
    void removeCloseTabListener(@NonNull TabCloseListener listener);

    /**
     * Returns the layout of the tab switcher.
     *
     * @return The layout of the tab switcher as a value of the enum {@link Layout}
     */
    @NonNull
    Layout getLayout();

    /**
     * Returns, whether an animation is currently running, or not.
     *
     * @return True, if an animation is currently running, false otherwise
     */
    boolean isAnimationRunning();

    /**
     * Returns, whether the tab switcher is empty, or not.
     *
     * @return True, if the tab switcher is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of tabs, which are contained by the tab switcher.
     *
     * @return The number of tabs, which are contained by the tab switcher, as an {@link Integer}
     * value
     */
    int getCount();

    /**
     * Returns the tab at a specific index.
     *
     * @param index
     *         The index of the tab, which should be returned, as an {@link Integer} value. The
     *         index must be at least 0 and at maximum <code>getCount() - 1</code>, otherwise a
     *         {@link IndexOutOfBoundsException} will be thrown
     * @return The tab, which corresponds to the given index, as an instance of the class {@link
     * Tab}. The tab may not be null
     */
    @NonNull
    Tab getTab(int index);

    /**
     * Returns the index of a specific tab.
     *
     * @param tab
     *         The tab, whose index should be returned, as an instance of the class {@link Tab}. The
     *         tab may not be null
     * @return The index of the given tab as an {@link Integer} value or -1, if the given tab is not
     * contained by the tab switcher
     */
    int indexOf(@NonNull Tab tab);

    /**
     * Adds a new tab to the tab switcher. By default, the tab is added at the end. If the switcher
     * is currently shown, the tab is added by using an animation. By default, the animation type
     * <code>AnimationType.SWIPE_RIGHT</code> is used. If an animation is currently running, the tab
     * will be added once all previously started animations have been finished.
     *
     * @param tab
     *         The tab, which should be added, as an instance of the class {@link Tab}. The tab may
     *         not be null
     */
    void addTab(@NonNull Tab tab);

    /**
     * Adds a new tab to the tab switcher at a specific index. If the switcher is currently shown,
     * the tab is added by using an animation. By default, the animation type
     * <code>AnimationType.SWIPE_RIGHT</code> is used. If an animation is currently running, the tab
     * will be added once all previously started animations have been finished.
     *
     * @param tab
     *         The tab, which should be added, as an instance of the class {@link Tab}. The tab may
     *         not be null
     * @param index
     *         The index, the tab should be added at, as an {@link Integer} value. The index must be
     *         at least 0 and at maximum <code>getCount()</code>, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     */
    void addTab(@NonNull Tab tab, int index);

    /**
     * Adds a new tab to the tab switcher at a specific index. If the switcher is currently shown,
     * the tab is added by using an animation of a specific type. If an animation is currently
     * running, the tab will be added once all previously started animations have been finished.
     *
     * @param tab
     *         The tab, which should be added, as an instance of the class {@link Tab}. The tab may
     *         not be null
     * @param index
     *         The index, the tab should be added at, as an {@link Integer} value. The index must be
     *         at least 0 and at maximum <code>getCount()</code>, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     * @param animation
     *         The animation, which should be used to add the tab, as an instance of the class
     *         {@link Animation}. The animation may not be null
     */
    void addTab(@NonNull Tab tab, int index, @NonNull Animation animation);

    /**
     * Removes a specific tab from the tab switcher. If the switcher is currently shown, the tab is
     * removed by using an animation. By default, the animation type <code>AnimationType.SWIPE_RIGHT</code>
     * is used. If an animation is currently running, the tab will be removed once all previously
     * started animations have been finished.
     *
     * @param tab
     *         The tab, which should be removed, as an instance of the class {@link Tab}. The tab
     *         may not be null
     */
    void removeTab(@NonNull Tab tab);

    /**
     * Removes a specific tybe from the tab switcher. If the switcher is currently shown, the tab is
     * removed by using an animation of a specific type. If an animation is currently running, the
     * tab will be removed once all previously started animations have been finished.
     *
     * @param tab
     *         The tab, which should be removed, as an instance of the class {@link Tab}. The tab
     *         may not be null
     * @param animation
     *         The animation, which should be used to remove the tabs, as an instance of the class
     *         {@link Animation}. The animation may not be null
     */
    void removeTab(@NonNull Tab tab, @NonNull Animation animation);

    /**
     * Removes all tabs from the tab switcher. If the switcher is currently shown, the tabs are
     * removed by using an animation. By default, the animation type <code>AnimationType.SWIPE_RIGHT</code>
     * is used. If an animation is currently running, the tabs will be removed once all previously
     * started animations have been finished.
     */
    void clear();

    /**
     * Removes all tabs from the tab switcher. If the switcher is currently shown, the tabs are
     * removed by using an animation of a specific type. If an animation is currently running, the
     * tabs will be removed once all previously started animations have been finished.
     *
     * @param animation
     *         The animation, which should be used to remove the tabs, as an instance of the class
     *         {@link Animation}. The animation may not be null
     */
    void clear(@NonNull Animation animation);

    /**
     * Returns, whether the tab switcher is currently shown.
     *
     * @return True, if the tab switcher is currently shown, false otherwise
     */
    boolean isSwitcherShown();

    /**
     * Shows the tab switcher by using an animation, if it is not already shown.
     */
    void showSwitcher();

    /**
     * Hides the tab switcher by using an animation, if it is currently shown.
     */
    void hideSwitcher();

    /**
     * Toggles the visibility of the tab switcher by using an animation, i.e. if the switcher is
     * currently shown, it is hidden, otherwise it is shown.
     */
    void toggleSwitcherVisibility();

    /**
     * Returns the currently selected tab.
     *
     * @return The currently selected tab as an instance of the class {@link Tab} or null, if no tab
     * is currently selected
     */
    @Nullable
    Tab getSelectedTab();

    /**
     * Returns the index of the currently selected tab.
     *
     * @return The index of the currently selected tab as an {@link Integer} value or -1, if no tab
     * is currently selected
     */
    int getSelectedTabIndex();

    /**
     * Selects a specific tab.
     *
     * @param tab
     *         The tab, which should be selected, as an instance of the class {@link Tab}. The tab
     *         may not be null. If the tab is not contained by the tab switcher, a {@link
     *         NoSuchElementException} will be thrown
     */
    void selectTab(@NonNull Tab tab);

    /**
     * Returns the view group, which contains the tab switcher's tabs.
     *
     * @return The view group, which contains the tab switcher's tabs, as an instance of the class
     * {@link ViewGroup}. The view group may not be null
     */
    @NonNull
    ViewGroup getTabContainer();

    /**
     * Returns the toolbar, which is shown, when the tab switcher is shown.
     *
     * @return The toolbar, which is shown, when the tab switcher is shown, as an instance of the
     * class {@link Toolbar}. The toolbar may not be null
     */
    @NonNull
    Toolbar getToolbar();

    /**
     * Sets, whether the toolbar should be shown, when the tab switcher is shown, or not.
     *
     * @param show
     *         True, if the toolbar should be shown, false otherwise
     */
    void showToolbar(boolean show);

    /**
     * Returns, whether the toolbar is shown, when the tab switcher is shown, or not.
     *
     * @return True, if the toolbar is shown, false otherwise
     */
    boolean isToolbarShown();

    /**
     * Sets the title of the toolbar, which is shown, when the tab switcher is shown.
     *
     * @param title
     *         The title, which should be set, as an instance of the type {@link CharSequence} or
     *         null, if no title should be set
     */
    void setToolbarTitle(@Nullable CharSequence title);

    /**
     * Sets the title of the toolbar, which is shown, when the tab switcher is shown.
     *
     * @param resourceId
     *         The resource id of the title, which should be set, as an {@link Integer} value. The
     *         resource id must correspond to a valid string resource
     */
    void setToolbarTitle(@StringRes int resourceId);

    /**
     * Inflates the menu of the toolbar, which is shown, when the tab switcher is shown.
     *
     * @param resourceId
     *         The resource id of the menu, which should be inflated, as an {@link Integer} value.
     *         The resource id must correspond to a valid menu resource
     * @param listener
     *         The listener, which should be notified, when an menu item has been clicked, as an
     *         instance of the type {@link OnMenuItemClickListener} or null, if no listener should
     *         be notified
     */
    void inflateToolbarMenu(@MenuRes int resourceId, @Nullable OnMenuItemClickListener listener);

    /**
     * Returns the menu of the toolbar, which is shown, when the tab switcher is shown.
     *
     * @return The menu of the toolbar as an instance of the type {@link Menu}. The menu may not be
     * null
     */
    @NonNull
    Menu getToolbarMenu();

    /**
     * Sets the navigation icon of the toolbar, which is shown, when the tab switcher is shown.
     *
     * @param icon
     *         The icon, which should be set, as an instance of the class {@link Drawable} or null,
     *         if no icon should be set
     * @param listener
     *         The listener, which should be notified, when the navigation item has been clicked, as
     *         an instance of the type {@link OnClickListener} or null, if no listener should be
     *         notified
     */
    void setToolbarNavigationIcon(@Nullable Drawable icon, @Nullable OnClickListener listener);

    /**
     * Sets the navigation icon of the toolbar, which is shown, when the tab switcher is shown.
     *
     * @param resourceId
     *         The resource id of the icon, which should be set, as an {@link Integer} value. The
     *         resource id must correspond to a valid drawable resource
     * @param listener
     *         The listener, which should be notified, when the navigation item has been clicked, as
     *         an instance of the type {@link OnClickListener} or null, if no listener should be
     *         notified
     */
    void setToolbarNavigationIcon(@DrawableRes int resourceId, @Nullable OnClickListener listener);

    /**
     * Sets the padding of the tab switcher.
     *
     * @param left
     *         The left padding, which should be set, as an {@link Integer} value
     * @param top
     *         The top padding, which should be set, as an {@link Integer} value
     * @param right
     *         The right padding, which should be set, as an {@link Integer} value
     * @param bottom
     *         The bottom padding, which should be set, as an {@link Integer} value
     */
    void setPadding(int left, int top, int right, int bottom);

    /**
     * Returns the left padding of the tab switcher.
     *
     * @return The left padding of the tab switcher as an {@link Integer} value
     */
    int getPaddingLeft();

    /**
     * Returns the top padding of the tab switcher.
     *
     * @return The top padding of the tab switcher as an {@link Integer} value
     */
    int getPaddingTop();

    /**
     * Returns the right padding of the tab switcher.
     *
     * @return The right padding of the tab switcher as an {@link Integer} value
     */
    int getPaddingRight();

    /**
     * Returns the bottom padding of the tab switcher.
     *
     * @return The bottom padding of the tab switcher as an {@link Integer} value
     */
    int getPaddingBottom();

    /**
     * Returns the start padding of the tab switcher. This corresponds to the right padding, if a
     * right-to-left layout is used, or to the left padding otherwise.
     *
     * @return The start padding of the tab switcher as an {@link Integer} value
     */
    int getPaddingStart();

    /**
     * Returns the end padding of the tab switcher. This corresponds ot the left padding, if a
     * right-to-left layout is used, or to the right padding otherwise.
     *
     * @return The end padding of the tab switcher as an {@link Integer} value
     */
    int getPaddingEnd();

    /**
     * Returns the background color of a tab.
     *
     * @return The background color of a tab as an {@link Integer} value
     */
    @ColorInt
    int getTabBackgroundColor();

    /**
     * Sets the background color of a tab.
     *
     * @param color
     *         The color, which should be set, as an {@link Integer} value
     */
    void setTabBackgroundColor(@ColorInt int color);

    /**
     * Returns the text color of a tab's title.
     *
     * @return The text color of a tab's title as an {@link Integer} value
     */
    @ColorInt
    int getTabTitleTextColor();

    /**
     * Sets the text color of a tab's title.
     *
     * @param color
     *         The color, which should be set, as an {@link Integer} value
     */
    void setTabTitleTextColor(@ColorInt int color);

}