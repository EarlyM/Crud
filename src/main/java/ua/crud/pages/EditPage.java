package ua.crud.pages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import ua.crud.model.User;
import ua.crud.service.UserService;

@AuthorizeInstantiation(value = "ROLE_USER")
public class EditPage extends WebPage {

    @SpringBean
    private UserService userService;

    public EditPage(PageParameters parameters) {
        StringValue s = parameters.get("userId");

        User user = s.isNull() ? null : userService.getUserById(s.toLong());


        Form form = new Form("userForm");
        TextField id = new TextField("id");
        form.add(id);
        id.setVisible(false);
        TextField username = new TextField("username");
        form.add(username);
        TextField password = new TextField("password");
        form.add(password);
        TextField fio = new TextField("fio");
        form.add(fio);

        if(user != null){
            id.setModel(new Model(user.getId()));
            username.setModel(new Model(user.getLogin()));
            password.setModel(new Model(user.getPassword()));
            fio.setModel(new Model(user.getFio()));
        } else {
            id.setModel(new Model());
            username.setModel(new Model());
            password.setModel(new Model());
            fio.setModel(new Model());
        }

        Button button = new Button("sbm"){
            @Override
            public void onSubmit() {
                User user = new User();
                user.setId((Long) id.getModelObject());
                user.setLogin((String) username.getModelObject());
                user.setPassword((String) password.getModelObject());
                user.setFio((String) fio.getModelObject());
                if(null == user.getId()){
                    userService.createUser(user);
                } else {
                    userService.updateUser(user);
                }
                setResponsePage(HomePage.class);
            }
        };
        form.add(button);

        add(new Link("backLink") {
            @Override
            public void onClick() {
                setResponsePage(HomePage.class);
            }
        });


        add(form);
    }

    private static class UserForm extends StatelessForm{

        private Long id;
        private String username;
        private String password;
        private String fio;

        public UserForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));
        }

        public UserForm(String id, User user){
            super(id);
            setModel(new CompoundPropertyModel(this));
        }


    }
}
