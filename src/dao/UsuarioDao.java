package dao;

import model.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private File file;

    public UsuarioDao(){
        file = new File("Usuarios");

        // Criando o arquivo
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException exception){
                System.out.println("Falha ao criar arquivo");
            }
        }
    }

    public List<Usuario> listarUsuarios(){
        if(file.length()>0){
            try{
                ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream(file)
                );
                List<Usuario> lista = (List<Usuario>) in.readObject();
                return lista;
            }catch (IOException exception){
                System.out.println(exception);
            }catch (ClassNotFoundException exception) {
                System.out.println(exception);
            }
        }
        return new ArrayList<>();
    }

    private boolean atualizarArquivo(List<Usuario> lista){
        try{
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(file)
            );
            out.writeObject(lista);
            return true;
        }catch(IOException exception){
            System.out.println(exception);
        }
        return false;
    }

    public boolean addUsuario(Usuario usuario){
        if(buscarPorEmail(usuario.getEmail()) == null){
            List<Usuario> usuarios = listarUsuarios();
            if(usuarios.add(usuario)){
                atualizarArquivo(usuarios);
                return true;
            }
        }
        return false;
    }

    public boolean deletarUsuario(Usuario usuario){
        List<Usuario> usuarios = listarUsuarios();
        if(usuarios.remove(usuario)){
            atualizarArquivo(usuarios);
            return true;
        }
        return false;
    }

    public Usuario buscarPorEmail(String email){
        List<Usuario> usuarios = listarUsuarios();
        for(Usuario usuario : usuarios){
            if(usuario.getEmail().equals(email)){
                return usuario;
            }
        }
        return null;
    }

    public boolean atualizarUsuario(Usuario usuario){
        Usuario usuario1 = buscarPorEmail(usuario.getEmail());
        if(usuario1 != null){
            List<Usuario> usuarios = listarUsuarios();
            usuarios.remove(usuario1);
            usuarios.add(usuario);
            atualizarArquivo(usuarios);
            return true;
        }
        return false;
    }

}
