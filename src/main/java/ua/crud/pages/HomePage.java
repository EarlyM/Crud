package ua.crud.pages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ua.crud.model.User;
import ua.crud.service.UserService;

import java.util.List;

@AuthorizeInstantiation(value = "ROLE_USER")
@SuppressWarnings("unchecked")
public class HomePage extends WebPage {

    @SpringBean
    private UserService userService;
    private List<User> userList = null;

    public HomePage() {

        PropertyListView list = new PropertyListView("userList") {
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("login", new PropertyModel(item.getModel(), "login")));
                item.add(new Label("password", new PropertyModel(item.getModel(), "password")));
                item.add(new Label("fio", new PropertyModel(item.getModel(), "fio")));
                item.add(new Link("delete") {
                    @Override
                    public void onClick() {
                        userService.deleteUser(((User)item.getModelObject()).getId());
                        setResponsePage(HomePage.class);
                    }
                });
                item.add(new Link("editLink") {
                    @Override
                    public void onClick() {
                        setResponsePage(EditPage.class, new PageParameters().add("userId",  ((User)item.getModelObject()).getId()));
                    }
                });
            }
        };

        Link createUserLink = new Link("createNewUser") {
            @Override
            public void onClick() {
                setResponsePage(EditPage.class);
            }
        };
        add(createUserLink);

        Form form = new Form("searchForm");
        TextField textField = new TextField("searchValue", new Model());
        form.add(textField);
        form.add(new Button("btn"){
            @Override
            public void onSubmit() {
                String search = (String) textField.getModelObject();

                userList = userService.getUserByFio(search == null ? "" : search);
                list.setList(userList);
            }
        });
        add(form);


        if(userList == null){
            userList = userService.getAll();
            list.setList(userList);
        }

        add(new Link("logout") {
            @Override
            public void onClick() {
                getSession().invalidate();
            }
        });

        add(list);
    }


}
