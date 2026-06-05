package es.ulpgc.eite.da.advmasterdetail;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
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
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

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

    private void clearOnlySession() {
        Context context = getInstrumentation().getTargetContext();

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();

        AuthMediator.resetInstance();
        GameMediator.resetInstance();
    }

    private void relaunchRegisterScreen() {
        Intent intent = new Intent(
                getInstrumentation().getTargetContext(),
                RegisterActivity.class
        );

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        testRule.finishActivity();
        testRule.launchActivity(intent);

        waitForData();
    }

    private void waitForData() {
        SystemClock.sleep(1500);
    }

    private int color(int colorId) {
        return ContextCompat.getColor(
                getInstrumentation().getTargetContext(),
                colorId
        );
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
        registerUser(NAME, EMAIL, PASSWORD);
    }

    private void registerUser(String name, String email, String password) {
        onView(withId(R.id.name_edit_text))
                .perform(clearText(), typeText(name), closeSoftKeyboard());

        onView(withId(R.id.email_edit_text))
                .perform(clearText(), typeText(email), closeSoftKeyboard());

        onView(withId(R.id.password_edit_text))
                .perform(clearText(), typeText(password), closeSoftKeyboard());

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
        loginUser(EMAIL, PASSWORD);
    }

    private void loginUser(String email, String password) {
        onView(withId(R.id.email_edit_text))
                .perform(clearText(), typeText(email), closeSoftKeyboard());

        onView(withId(R.id.password_edit_text))
                .perform(clearText(), typeText(password), closeSoftKeyboard());

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

    private void assertLikeTextAtPosition(int position, String expectedNumber) {
        onView(withId(R.id.product_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position));

        waitForData();

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(position, R.id.product_likes))
                .check(matches(withText(containsString(expectedNumber))));
    }

    private void assertLikeAtPosition(
            int position, String expectedNumber, int expectedColor) {

        onView(withId(R.id.product_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position));

        waitForData();

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(position, R.id.product_likes))
                .check(matches(withText(containsString(expectedNumber))))
                .check(matches(withTextColor(expectedColor)));
    }

    private void assertGameVisibleAtPosition(int position) {
        onView(withId(R.id.product_recycler))
                .perform(RecyclerViewActions.scrollToPosition(position));

        waitForData();

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(position, R.id.product_name))
                .check(matches(isDisplayed()));
    }

    private void assertDetailViewVisibleWithScroll(int viewId) {
        onView(withId(viewId))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
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
                .perform(scrollTo())
                .check(matches(withText(containsString("PC, PlayStation 5, Xbox Series X|S"))));
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
                .perform(scrollTo())
                .check(matches(withText("RPG")));

        onView(withId(R.id.product_year))
                .perform(scrollTo())
                .check(matches(withText("2023")));

        onView(withId(R.id.product_developer))
                .perform(scrollTo())
                .check(matches(withText("Larian Studios")));

        onView(withId(R.id.product_likes))
                .perform(scrollTo())
                .check(matches(withText(containsString("0"))));

        onView(withId(R.id.product_detail))
                .perform(scrollTo())
                .check(matches(withText(containsString("Baldur's Gate 3 es un RPG"))));
    }

    @Test
    public void test07_likesVisiblesParaInvitadoPeroNoPuedeModificar() {
        enterAsGuest();

        int gray = color(R.color.gv_text_gray);

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

        int red = color(R.color.gv_red);

        clickFavoriteAtPosition(0);

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_likes))
                .check(matches(withText(containsString("1"))))
                .check(matches(withTextColor(red)));
    }

    @Test
    public void test09_quitarFavoritoDesdeLista() {
        registerAndLogin();

        int gray = color(R.color.gv_text_gray);

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
                .perform(scrollTo())
                .check(matches(withText(containsString("PC, PlayStation 5, Xbox Series X|S"))));

        onView(withId(R.id.product_detail))
                .perform(scrollTo())
                .check(matches(withText(containsString("Baldur's Gate 3 es un RPG"))));

        pressBack();

        waitForData();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));
    }

    @Test
    public void test13_rotacionScrollListaYDetalle() {
        enterAsGuest();

        rotateLeft();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Videojuegos")));

        assertGameVisibleAtPosition(0);
        assertGameVisibleAtPosition(6);
        assertGameVisibleAtPosition(12);

        rotateNatural();

        assertGameVisibleAtPosition(12);
        clickGameAtPosition(12);

        rotateLeft();

        onView(withId(R.id.detail_title))
                .check(matches(withText("Descripción")));

        assertDetailViewVisibleWithScroll(R.id.product_name);
        assertDetailViewVisibleWithScroll(R.id.product_platform);
        assertDetailViewVisibleWithScroll(R.id.product_detail);

        rotateNatural();
    }

    @Test
    public void test14_rotacionScrollEnListaFavoritos() {
        registerAndLogin();

        /*
         * Marcamos favoritos en distintas posiciones para obligar a usar scroll.
         */
        clickFavoriteAtPosition(0);
        clickFavoriteAtPosition(6);
        clickFavoriteAtPosition(12);

        onView(withId(R.id.favorites_button))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(3)));

        /*
         * Giramos en la pantalla de favoritos.
         */
        rotateLeft();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(3)));

        /*
         * Hacemos scroll dentro de favoritos.
         */
        onView(withId(R.id.product_recycler))
                .perform(RecyclerViewActions.scrollToPosition(2));

        waitForData();

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(2, R.id.product_name))
                .check(matches(isDisplayed()));

        /*
         * Entramos al detalle desde favoritos después de hacer scroll.
         */
        clickGameAtPosition(2);

        onView(withId(R.id.detail_title))
                .check(matches(withText("Descripción")));

        assertDetailViewVisibleWithScroll(R.id.product_name);
        assertDetailViewVisibleWithScroll(R.id.product_platform);

        rotateNatural();

        assertDetailViewVisibleWithScroll(R.id.product_name);

        pressBack();

        waitForData();

        /*
         * Al volver, seguimos en favoritos.
         */
        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(3)));
    }

    @Test
    public void test15_flujoCompletoUsuariosInvitadoFavoritosPersistencia() {

        String user1Name = "Arlyn";
        String user1Email = "arlyn@test.com";
        String user1Password = "1234";

        String user2Name = "Lara";
        String user2Email = "lara@test.com";
        String user2Password = "1234";

        int gray = color(R.color.gv_text_gray);
        int red = color(R.color.gv_red);

        /*
         * 1. Invitado:
         * ve lista, ve likes, pero no puede marcar favoritos.
         */
        enterAsGuest();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Videojuegos")));

        onView(withId(R.id.favorites_button))
                .check(matches(not(isDisplayed())));

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_name))
                .check(matches(withText("Baldur's Gate 3")));

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_likes))
                .check(matches(withText(containsString("0"))))
                .check(matches(withTextColor(gray)));

        clickFavoriteAtPosition(0);

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_likes))
                .check(matches(withText(containsString("0"))))
                .check(matches(withTextColor(gray)));

        /*
         * 2. Usuario 1:
         * se registra, inicia sesión y marca favoritos en posiciones separadas.
         */
        clearOnlySession();
        relaunchRegisterScreen();

        registerUser(user1Name, user1Email, user1Password);

        onView(withId(R.id.login_button))
                .check(matches(isDisplayed()));

        loginUser(user1Email, user1Password);

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Videojuegos")));

        onView(withId(R.id.favorites_button))
                .check(matches(isDisplayed()));

        clickFavoriteAtPosition(0);
        assertLikeAtPosition(0, "1", red);

        clickFavoriteAtPosition(6);
        assertLikeAtPosition(6, "1", red);

        clickFavoriteAtPosition(12);
        assertLikeAtPosition(12, "1", red);

        /*
         * 3. Lista de favoritos del usuario 1.
         */
        onView(withId(R.id.favorites_button))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(3)));

        assertGameVisibleAtPosition(0);
        assertGameVisibleAtPosition(1);
        assertGameVisibleAtPosition(2);

        /*
         * 4. Giro y scroll dentro de favoritos.
         */
        rotateLeft();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(3)));

        onView(withId(R.id.product_recycler))
                .perform(RecyclerViewActions.scrollToPosition(2));

        waitForData();

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(2, R.id.product_name))
                .check(matches(isDisplayed()));

        rotateNatural();

        /*
         * 5. Detalle reutilizado desde favoritos.
         */
        clickGameAtPosition(2);

        onView(withId(R.id.detail_title))
                .check(matches(withText("Descripción")));

        assertDetailViewVisibleWithScroll(R.id.product_name);
        assertDetailViewVisibleWithScroll(R.id.product_platform);

        onView(withId(R.id.product_likes))
                .perform(scrollTo())
                .check(matches(withText(containsString("1"))));

        pressBack();

        waitForData();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        /*
         * 6. Volver como invitado:
         * ve los likes totales, pero sin favoritos personales.
         */
        clearOnlySession();
        relaunchRegisterScreen();

        enterAsGuest();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Videojuegos")));

        onView(withId(R.id.favorites_button))
                .check(matches(not(isDisplayed())));

        assertLikeAtPosition(0, "1", gray);
        assertLikeAtPosition(6, "1", gray);
        assertLikeAtPosition(12, "1", gray);

        /*
         * 7. Usuario 2:
         * se registra, ve likes totales del usuario 1, pero sus corazones están grises.
         */
        clearOnlySession();
        relaunchRegisterScreen();

        registerUser(user2Name, user2Email, user2Password);
        loginUser(user2Email, user2Password);

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Videojuegos")));

        assertLikeAtPosition(0, "1", gray);
        assertLikeAtPosition(6, "1", gray);
        assertLikeAtPosition(12, "1", gray);

        /*
         * Usuario 2 marca un juego que ya había marcado usuario 1.
         * El total sube a 2, pero solo para ese juego.
         */
        clickFavoriteAtPosition(6);
        assertLikeAtPosition(6, "2", red);

        /*
         * En favoritos del usuario 2 solo aparece ese juego.
         */
        onView(withId(R.id.favorites_button))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(1)));

        onView(new RecyclerViewMatcher(R.id.product_recycler)
                .atPositionOnView(0, R.id.product_name))
                .check(matches(isDisplayed()));

        /*
         * 8. Persistencia:
         * usuario 1 vuelve a iniciar sesión y mantiene sus favoritos.
         */
        clearOnlySession();
        relaunchRegisterScreen();

        openLoginFromRegister();
        loginUser(user1Email, user1Password);

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Videojuegos")));

        assertLikeAtPosition(0, "1", red);
        assertLikeAtPosition(6, "2", red);
        assertLikeAtPosition(12, "1", red);

        onView(withId(R.id.favorites_button))
                .perform(click());

        waitForData();

        onView(withId(R.id.product_list_title))
                .check(matches(withText("Favoritos")));

        onView(withId(R.id.product_recycler))
                .check(matches(withRecyclerViewItemCount(3)));

        assertGameVisibleAtPosition(0);
        assertGameVisibleAtPosition(1);
        assertGameVisibleAtPosition(2);
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