package view;

import dao.UsuarioDao;
import model.Usuario;

import javax.swing.*;
import java.util.List;

public class Main {

    private static final String MENU_MESSAGE = "1 - Adicionar Usuário\n2 - Listar Usuários\n3 - Buscar por Email\n0 - Sair";

    private static final UsuarioDao dao = new UsuarioDao();

    public static void main(String[] args) {
        int choice;

        do {
            String input = JOptionPane.showInputDialog(MENU_MESSAGE);
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            processarEscolha(choice);

        } while (choice != 0);
    }

    private static void processarEscolha(int choice) {
        switch (choice) {
            case 1:
                adicionarUsuario();
                break;
            case 2:
                listarUsuarios();
                break;
            case 3:
                buscarPorEmail();
                break;
            case 0:
                JOptionPane.showMessageDialog(null, "Saindo do programa");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Escolha inválida. Tente novamente.");
        }
    }

    private static void adicionarUsuario() {
        String nome = JOptionPane.showInputDialog("Digite o nome do usuário:");
        String email = JOptionPane.showInputDialog("Digite o email do usuário:");
        Usuario novoUsuario = new Usuario(nome, email);
        boolean added = dao.addUsuario(novoUsuario);
        exibirMensagemOperacao(added, "Usuário adicionado com sucesso!", "Falha ao adicionar usuário.");
    }

    private static void listarUsuarios() {
        List<Usuario> usuarios = dao.listarUsuarios();
        JOptionPane.showMessageDialog(null, "Lista de Usuários:\n" + usuarios);
    }

    private static void buscarPorEmail() {
        String searchEmail = JOptionPane.showInputDialog("Digite o email a ser buscado:");
        Usuario foundUser = dao.buscarPorEmail(searchEmail);
        exibirMensagemUsuarioEncontrado(foundUser);
    }

    private static void exibirMensagemOperacao(boolean sucesso, String sucessoMsg, String falhaMsg) {
        JOptionPane.showMessageDialog(null, sucesso ? sucessoMsg : falhaMsg);
    }

    private static void exibirMensagemUsuarioEncontrado(Usuario foundUser) {
        if (foundUser != null) {
            JOptionPane.showMessageDialog(null, "Usuário encontrado:\n" + foundUser);
        } else {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
        }
    }
}
