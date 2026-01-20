### Partie 1

Classe user avec un email, un password et un role
Une enumération role avec USER et admin

L'email est non null, non vide et contient un seul @ puis au moins un . après
Le mdp est non null, non vide et à les memes regles qu'avant
Le role est non nul et à seulement deux valeurs autorisées (celle de l'enum)

L'email est trimer->trim()

La classe User expose la méthode boolean canAccessAdminArea(), retourne true si ADMIN, retourne false sinon

En cas de non respect des erreurs une IllegalArgumentException est levé avec des meessages explicite

Classe UserService qui expose la méthode de creation d'un user

Tests unitaires à créer, règles OK sur user, méthode canAccessAdminArea(), cas limites et cas d'erreur

Test fonctionnels sur le register, sur la validation du comportement métier global


public boolean canAccessAdminArea(User user){
    
    if(user.equals("ADMIN")){
        returnn true
    }

    return false
}


Cas nominaux :
email_is_correct()
password_is_correct()
role_is_correct()

Cas limites :
email_has_two_point()
email_is_bigger_that_twenty-four_caracteres()

password_has_height_caracteres()

Cas d'erreur :
email_is_null()
email_is_blank()
email_has_two_@()
email_has_no_point()
email_has_no_@()
email_start_with_@()

password_is_null()
password_is_blank()
password_does_not_contains_special_caractere()
password_does_not_contains_a_minus_case()
password_does_not_contains_a_upper_case()
password_does_not_contain_a_number()
password_is_under_height_caracteres()

role_is_null()
role_is_different_from_USER_or_ADMIN()

Other :
user_canAccessAdminArea_method_is_correct()

userService_create_user_is_correct()
