package es.ulpgc.eite.da.advmasterdetail;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import android.content.Context;
import android.os.RemoteException;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import es.ulpgc.eite.da.advmasterdetail.app.AuthMediator;
import es.ulpgc.eite.da.advmasterdetail.app.GameMediator;
import es.ulpgc.eite.da.advmasterdetail.auth.RegisterActivity;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepository;

@SuppressWarnings("ALL")
@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameVaultEspressoTests {

    private static final String DB_NAME = "gamevault.db";
    private static final String PREFS_NAME = "gamevault_session";

    private static final String NAME = "Arlyn";
    private static final String EMAIL = "arlyn@test.com";
    private static final String PASSWORD = "1234";

    @Rule
    public ActivityTestRule<RegisterActivity> testRule =
            new ActivityTestRule<>(RegisterActivity.class, true, false);

    @Before
    public void setUp() {
        resetAppData();
        testRule.launchActivity(null);
        waitForData();
    }

    @After
    public void tearDown() {
        try {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            device.setOrientationNatural();
        } catch (RemoteException ignored) {
        }
    }

    private void resetAppData() {
        Context context = getInstrumentation().getTargetContext();

        GameRepository.resetInstance();
        GameMediator.resetInstance();
        AuthMediator.resetInstance();

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();

        context.deleteDatabase(DB_NAME);
    }

    private void waitForData() {
        SystemClock.sleep(1000);
    }

    private void rotateLeft() {
        try {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            device.setOrientationLeft();
            waitForData();
        } catch (RemoteException ignored) {
        }
    }

    private void rotateNatural() {
        try {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            device.setOrientationNatural();
            waitForData();
        } catch (RemoteException ignored) {
        }
    }

    private void registerUser() {
        onView(withId(R.id.name_edit_text))
                .perform(clearText(), typeText(NAME), closeSoftKeyboard());

        onView(withId(R.id.email_edit_text))
                .perform(clearText(), typeText(EMAIL), closeSoftKeyboard());

        onView(withId(R.id.password_edit_text))
                .perform(clearText(), typeText(PASSWORD), closeSoftKeyboard());

        onView(withId(R.id.register_button))
                .perform(click());

        waitForData();
    }

    private void openLoginFromRegister() {
        onView(withId(R.id.go_login_text))
                .perform(click());

        waitForData();
    }

    private void loginUser() {
        onView(withId(R.id.email_edit_text))
                .perform(clearText(), typeText(EMAIL), closeSoftKeyboard());

        onView(withId(R.id.password_edit_text))
                .perform(clearText(), typeText(PASSWORD), closeSoftKeyboard());

        onView(withId(R.id.login_button))
                .perform(click());

        waitForData();
    }

    private void registerAndLogin() {
        registerUser();
        loginUser();
        waitForData();
    }

    private void enterAsGuest() {
        onView(withId(R.id.guest_text))
                .perform(click());

        waitForData();
    }

    private void clickGameAtPosition(int position) {
        onView(withId(R.id.product_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position));

        waitForData();

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPosition(position))
                .perform(click());

        waitForData();
    }

    private void clickFavoriteAtPosition(int position) {
        onView(withId(R.id.product_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position));

        waitForData();

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(position, R.id.product_likes))
                .perform(click());

        waitForData();
    }

    @Test
    public void test01_registroUsuario() {
        registerUser();

        onView(withId(R.id.login_button))
                .check(matches(isDisplayed()));

        onView(withId(R.id.login_screen_title))
                .check(matches(withText("Login")));
    }

    @Test
    public void test02_loginCorrecto() {
        registerUser();
        loginUser();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Videojuegos")));

        onView(withId(R.id.product_recycler))
                .check(matches(isDisplayed()));

        onView(withId(R.id.favorites_button))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test03_loginIncorrecto() {
        registerUser();

        onView(withId(R.id.email_edit_text))
                .perform(clearText(), typeText(EMAIL), closeSoftKeyboard());

        onView(withId(R.id.password_edit_text))
                .perform(clearText(), typeText("mal"), closeSoftKeyboard());

        onView(withId(R.id.login_button))
                .perform(click());

        waitForData();

        onView(withId(R.id.login_button))
                .check(matches(isDisplayed()));

        onView(withId(R.id.login_screen_title))
                .check(matches(withText("Login")));
    }

    @Test
    public void test04_listaGeneralInvitado() {
        enterAsGuest();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Videojuegos")));

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_name))
                .check(matches(withText("Baldur's Gate 3")));

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_genre))
                .check(matches(withText("RPG")));

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(1, R.id.product_name))
                .check(matches(withText("Red Dead Redemption 2")));

        onView(withId(R.id.favorites_button))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void test05_plataformaSoloApareceEnDetalle() {
        enterAsGuest();

        onView(withText("PC, PlayStation 5, Xbox Series X|S"))
                .check(doesNotExist());

        clickGameAtPosition(0);

        onView(withId(R.id.product_name))
                .check(matches(withText("Baldur's Gate 3")));

        onView(withId(R.id.product_platform))
                .check(matches(withText("PC, PlayStation 5, Xbox Series X|S")));
    }

    @Test
    public void test06_detalleVideojuego() {
        enterAsGuest();

        clickGameAtPosition(0);

        onView(withId(R.id.detail_title))
                .check(matches(withText("Descripción")));

        onView(withId(R.id.product_name))
                .check(matches(withText("Baldur's Gate 3")));

        onView(withId(R.id.product_genre))
                .check(matches(withText("RPG")));

        onView(withId(R.id.product_year))
                .check(matches(withText("2023")));

        onView(withId(R.id.product_developer))
                .check(matches(withText("Larian Studios")));

        onView(withId(R.id.product_detail))
                .check(matches(withText(containsString("Baldur's Gate 3 es un RPG"))));
    }

    @Test
    public void test07_likesVisiblesParaInvitadoPeroNoPuedeModificar() {
        enterAsGuest();

        int gray = ContextCompat.getColor(
                testRule.getActivity(),
                R.color.gv_text_gray
        );

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_likes))
                .check(matches(withText(containsString("0"))))
                .check(matches(withTextColor(gray)));

        clickFavoriteAtPosition(0);

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_likes))
                .check(matches(withText(containsString("0"))))
                .check(matches(withTextColor(gray)));
    }

    @Test
    public void test08_marcarFavoritoDesdeLista() {
        registerAndLogin();

        int red = ContextCompat.getColor(
                testRule.getActivity(),
                R.color.gv_red
        );

        clickFavoriteAtPosition(0);

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_likes))
                .check(matches(withText(containsString("1"))))
                .check(matches(withTextColor(red)));
    }

    @Test
    public void test09_quitarFavoritoDesdeLista() {
        registerAndLogin();

        int gray = ContextCompat.getColor(
                testRule.getActivity(),
                R.color.gv_text_gray
        );

        clickFavoriteAtPosition(0);

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_likes))
                .check(matches(withText(containsString("1"))));

        clickFavoriteAtPosition(0);

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_likes))
                .check(matches(withText(containsString("0"))))
                .check(matches(withTextColor(gray)));
    }

    @Test
    public void test10_listaFavoritosSoloMuestraFavoritosDelUsuario() {
        registerAndLogin();

        clickFavoriteAtPosition(0);

        onView(withId(R.id.favorites_button))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(1)));

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_name))
                .check(matches(withText("Baldur's Gate 3")));
    }

    @Test
    public void test11_quitarFavoritoDesdeListaFavoritos() {
        registerAndLogin();

        clickFavoriteAtPosition(0);

        onView(withId(R.id.favorites_button))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(1)));

        clickFavoriteAtPosition(0);

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(0)));
    }

    @Test
    public void test12_detalleReutilizadoDesdeFavoritos() {
        registerAndLogin();

        clickFavoriteAtPosition(0);

        onView(withId(R.id.favorites_button))
                .perform(click());

        waitForData();

        clickGameAtPosition(0);

        onView(withId(R.id.detail_title))
                .check(matches(withText("Descripción")));

        onView(withId(R.id.product_name))
                .check(matches(withText("Baldur's Gate 3")));

        onView(withId(R.id.product_platform))
                .check(matches(withText("PC, PlayStation 5, Xbox Series X|S")));

        onView(withId(R.id.product_detail))
                .check(matches(withText(containsString("Baldur's Gate 3 es un RPG"))));

        pressBack();

        waitForData();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));
    }

    @Test
    public void test13_rotacionEnListaYDetalle() {
        enterAsGuest();

        rotateLeft();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Videojuegos")));

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_name))
                .check(matches(withText("Baldur's Gate 3")));

        rotateNatural();

        clickGameAtPosition(0);

        rotateLeft();

        onView(withId(R.id.product_name))
                .check(matches(withText("Baldur's Gate 3")));

        onView(withId(R.id.product_platform))
                .check(matches(withText("PC, PlayStation 5, Xbox Series X|S")));

        rotateNatural();
    }

    @Test
    public void test14_marcarFavoritoDesdeDetalle() {
        registerAndLogin();

        int red = ContextCompat.getColor(
                testRule.getActivity(),
                R.color.gv_red
        );

        clickGameAtPosition(0);

        onView(withId(R.id.product_likes))
                .check(matches(withText(containsString("0"))));

        onView(withId(R.id.product_likes))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_likes))
                .check(matches(withText(containsString("1"))))
                .check(matches(withTextColor(red)));

        pressBack();

        waitForData();

        onView(withId(R.id.favorites_button))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(1)));

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_name))
                .check(matches(withText("Baldur's Gate 3")));
    }

    @Test
    public void test15_quitarFavoritoDesdeDetalle() {
        registerAndLogin();

        int gray = ContextCompat.getColor(
                testRule.getActivity(),
                R.color.gv_text_gray
        );

        clickFavoriteAtPosition(0);

        clickGameAtPosition(0);

        onView(withId(R.id.product_likes))
                .check(matches(withText(containsString("1"))));

        onView(withId(R.id.product_likes))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_likes))
                .check(matches(withText(containsString("0"))))
                .check(matches(withTextColor(gray)));

        pressBack();

        waitForData();

        onView(withId(R.id.favorites_button))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(0)));
    }

    private static Matcher<View> withTextColor(final int expectedColor) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with text color: " + expectedColor);
            }

            @Override
            protected boolean matchesSafely(View view) {
                if (!(view instanceof TextView)) {
                    return false;
                }

                TextView textView = (TextView) view;
                return textView.getCurrentTextColor() == expectedColor;
            }
        };
    }

    private static Matcher<View> withRecyclerViewItemCount(final int expectedCount) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("RecyclerView should have " + expectedCount + " items");
            }

            @Override
            protected boolean matchesSafely(View view) {
                if (!(view instanceof RecyclerView)) {
                    return false;
                }

                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();

                return adapter != null && adapter.getItemCount() == expectedCount;
            }
        };
    }
}