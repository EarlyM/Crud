package ua.crud.pages;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;


public class LoginPage extends WebPage {
    public LoginPage() {
        if(AuthenticatedWebSession.get().isSignedIn()){
            setResponsePage(HomePage.class);
        }
        final LoginForm form = new LoginForm("loginForm");
        add(form);
    }

    private static class LoginForm extends StatelessForm{

        private String username;
        private String password;

        public LoginForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));
            add(new Label("usernameLabel", "Username"));
            add(new RequiredTextField("username"));
            add(new Label("passwordLabel", "Password"));
            add(new PasswordTextField("password"));
            add(new FeedbackPanel("feedback"));
        }

        @Override
        protected void onSubmit() {
            AuthenticatedWebSession session = AuthenticatedWebSession.get();
            if(session.signIn(username, password)){
                setResponsePage(getApplication().getHomePage());
            }else {
              error("Не верный логин/пароль");
            }
        }
    }
}
