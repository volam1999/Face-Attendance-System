# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'login.ui'
#
# Created by: PyQt5 UI code generator 5.15.2
#
# WARNING: Any manual changes made to this file will be lost when pyuic5 is
# run again.  Do not edit this file unless you know what you are doing.


from PyQt5 import QtCore, QtGui, QtWidgets

class MainScreen(QtWidgets.QMainWindow):
    def __init__(self):
         # init main screen window
        QtWidgets.QMainWindow.__init__(self)
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)

        # REMOVE TITLE BAR
        self.setWindowFlag(QtCore.Qt.FramelessWindowHint)

        self.show()

class Ui_LoginScreen(object):
    def setupUi(self, LoginScreen):
        LoginScreen.setObjectName("LoginScreen")
        LoginScreen.resize(420, 600)
        LoginScreen.setAutoFillBackground(False)
        LoginScreen.setStyleSheet("")
        
        self.centralwidget = QtWidgets.QWidget(LoginScreen)
        self.centralwidget.setObjectName("centralwidget")
        self.lbLogo = QtWidgets.QLabel(self.centralwidget)
        self.lbLogo.setGeometry(QtCore.QRect(60, 30, 311, 301))
        self.lbLogo.setText("")
        self.lbLogo.setPixmap(QtGui.QPixmap(
            "./default-logo.png"))
        self.lbLogo.setScaledContents(True)
        self.lbLogo.setObjectName("lbLogo")
        self.txtUsername = QtWidgets.QLineEdit(self.centralwidget)
        self.txtUsername.setGeometry(QtCore.QRect(60, 370, 300, 40))
        font = QtGui.QFont()
        font.setPointSize(14)
        self.txtUsername.setFont(font)
        self.txtUsername.setStyleSheet("QLineEdit{\n"
                                       "    border: 2px solid rgb(255, 255, 255);\n"
                                       "    border-radius: 20px;\n"
                                       "    padding-left: 20px;\n"
                                       "    background-color: rgb(179, 182, 189);\n"
                                       "    padding-right: 20px;\n"
                                       "}\n"
                                       "\n"
                                       "QLineEdit:hover{\n"
                                       "    border: 2px solid rgb(255, 170, 0)\n"
                                       "}\n"
                                       "\n"
                                       "QLineEdit:focus{\n"
                                       "    border: 2px solid rgb(85, 255, 255)\n"
                                       "}")
        self.txtUsername.setObjectName("txtUsername")
        self.txtPassword = QtWidgets.QLineEdit(self.centralwidget)
        self.txtPassword.setGeometry(QtCore.QRect(60, 420, 300, 40))
        font = QtGui.QFont()
        font.setPointSize(14)
        self.txtPassword.setFont(font)
        self.txtPassword.setStyleSheet("QLineEdit{\n"
                                       "    border: 2px solid rgb(255, 255, 255);\n"
                                       "    border-radius: 20px;\n"
                                       "    padding-left: 20px;\n"
                                       "    background-color: rgb(179, 182, 189);\n"
                                       "    padding-right: 20px;\n"
                                       "}\n"
                                       "\n"
                                       "QLineEdit:hover{\n"
                                       "    border: 2px solid rgb(255, 170, 0)\n"
                                       "}\n"
                                       "\n"
                                       "QLineEdit:focus{\n"
                                       "    border: 2px solid rgb(85, 255, 255)\n"
                                       "}")
        self.txtPassword.setEchoMode(QtWidgets.QLineEdit.Password)
        self.txtPassword.setObjectName("txtPassword")
        self.btnLogin = QtWidgets.QPushButton(self.centralwidget)
        self.btnLogin.setGeometry(QtCore.QRect(130, 500, 151, 45))
        font = QtGui.QFont()
        font.setPointSize(14)
        font.setBold(True)
        font.setWeight(75)
        self.btnLogin.setFont(font)
        self.btnLogin.setStyleSheet("QPushButton{\n"
                                    "    border: 2px solid rgb(255, 255, 255);\n"
                                    "    color: \"#fff\";\n"
                                    "    background-color: rgb(63, 111, 222);\n"
                                    "    border-radius: 20px;\n"
                                    "}\n"
                                    "\n"
                                    "QPushButton:hover{\n"
                                    "    border: 2px solid rgb(255, 170, 0);\n"
                                    "}\n"
                                    "\n"
                                    "QPushButton:pressed{\n"
                                    "    background-color: rgba(63, 111, 222, 0.77);\n"
                                    "}\n"
                                    "\n"
                                    "\n"
                                    "")
        self.btnLogin.setObjectName("btnLogin")
        self.lbForgot = QtWidgets.QLabel(self.centralwidget)
        self.lbForgot.setGeometry(QtCore.QRect(220, 470, 131, 16))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.lbForgot.setFont(font)
        self.lbForgot.setCursor(QtGui.QCursor(QtCore.Qt.PointingHandCursor))
        self.lbForgot.setMouseTracking(False)
        self.lbForgot.setStyleSheet("QLabel{\n"
                                    "    color: rgb(63, 111, 222)\n"
                                    "}")
        self.lbForgot.setObjectName("lbForgot")
        self.cbRemember = QtWidgets.QCheckBox(self.centralwidget)
        self.cbRemember.setGeometry(QtCore.QRect(70, 470, 131, 17))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.cbRemember.setFont(font)
        self.cbRemember.setStyleSheet("color: rgb(151, 155, 165)")
        self.cbRemember.setObjectName("cbRemember")
        self.btnExit = QtWidgets.QPushButton(self.centralwidget)
        self.btnExit.setGeometry(QtCore.QRect(400, 0, 21, 21))
        self.btnExit.setAutoFillBackground(False)
        self.btnExit.setStyleSheet("QPushButton{\n"
                                   "    background-color: rgba(240, 240, 240, 0);\n"
                                   "    border: none\n"
                                   "}\n"
                                   "\n"
                                   "QPushButton:hover{\n"
                                   "    background-color: rgb(170, 255, 127)\n"
                                   "}")
        self.btnExit.setText("")
        icon = QtGui.QIcon()
        icon.addPixmap(QtGui.QPixmap(
            "./icons8_delete_16.png"), QtGui.QIcon.Normal, QtGui.QIcon.Off)
        self.btnExit.setIcon(icon)
        self.btnExit.setIconSize(QtCore.QSize(16, 16))
        self.btnExit.setObjectName("btnExit")
        LoginScreen.setCentralWidget(self.centralwidget)

        self.retranslateUi(LoginScreen)
        QtCore.QMetaObject.connectSlotsByName(LoginScreen)

        self.setEvents()

    def retranslateUi(self, LoginScreen):
        _translate = QtCore.QCoreApplication.translate
        LoginScreen.setWindowTitle(_translate("LoginScreen", "Login"))
        self.txtUsername.setPlaceholderText(
            _translate("LoginScreen", "Username"))
        self.txtPassword.setPlaceholderText(
            _translate("LoginScreen", "Password"))
        self.btnLogin.setText(_translate("LoginScreen", "Sign In"))
        self.lbForgot.setText(_translate("LoginScreen", "Forgot Password?"))
        self.cbRemember.setText(_translate("LoginScreen", "Remember me"))

    def setEvents(self):
        self.btnExit.clicked.connect(self.exit)
      
    def exit(self):
        import sys
        sys.exit()
