Imports System.Data.SqlClient
Imports System.IO

Public Class FormMenu
    Dim operation As String
    Dim sourceLoc As String = ""
    Dim endLoc As String = ""
    Dim imgName As String = ""

    Private Sub FormMenu_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        cbSort.SelectedItem = "Id"
        disAll()
        getMenu()
        pbMenu.ImageLocation = "MenuPic/" & "nopict.png"
    End Sub

    Private Sub getMenu(Optional keyword As String = "", Optional sort As String = "")
        Try
            openConn()
            query = "SELECT * FROM MsMenu"

            If keyword <> "" Then
                query &= "WHERE Id LIKE '%" & keyword & "%' OR Name LIKE '%" & keyword & "%' OR Price LIKE '%" & keyword & "%' OR Photo LIKE '%" & keyword & "%' OR Carbo LIKE '%" & keyword & "%' OR Protein LIKE '%" & keyword & "%'"
            End If
            If sort <> "" Then
                query &= "Order by '" & sort & "' asc"
            End If

            adapter = New SqlDataAdapter(query, conn)
            dataset.Clear()
            adapter.Fill(dataset, "Menu")

            With dg
                .DataSource = dataset
                .DataMember = "Menu"
                .Columns(3).Visible = False
            End With
        Catch ex As Exception

        End Try
    End Sub

    Private Sub enaAll()
        tbName.Enabled = True
        tbPrice.Enabled = True
        tbPhoto.Enabled = True
        tbCarbo.Enabled = True
        tbProtein.Enabled = True
        btnFile.Visible = True
        btnSave.Visible = True
        btnCancel.Visible = True

        btnInsert.Visible = False
        btnUpdate.Visible = False
        btnDelete.Visible = False
    End Sub

    Private Sub disAll()
        tbName.Enabled = False
        tbPrice.Enabled = False
        tbPhoto.Enabled = False
        tbCarbo.Enabled = False
        tbProtein.Enabled = False
        btnFile.Visible = False
        btnSave.Visible = False
        btnCancel.Visible = False

        btnInsert.Visible = True
        btnUpdate.Visible = True
        btnDelete.Visible = True
        setText()
    End Sub

    Private Sub setText(Optional id As String = "",
                        Optional name As String = "",
                        Optional price As String = "",
                        Optional photo As String = "",
                        Optional carbo As String = "",
                        Optional protein As String = "")
        tbId.Text = id
        tbName.Text = name
        tbPrice.Text = price
        tbPhoto.Text = photo
        tbCarbo.Text = carbo
        tbProtein.Text = protein
        pbMenu.ImageLocation = photo
        If photo <> "" Then
            pbMenu.ImageLocation = "MenuPic/" & photo
        End If
    End Sub

    Private Sub dg_CellClick(sender As Object, e As DataGridViewCellEventArgs) Handles dg.CellClick
        Dim i As Integer = dg.CurrentRow.Index

        With dg
            setText(.Item(0, i).Value, .Item(1, i).Value, .Item(2, i).Value, .Item(3, i).Value, .Item(4, i).Value, .Item(5, i).Value)
        End With
    End Sub

    Private Sub btnInsert_Click(sender As Object, e As EventArgs) Handles btnInsert.Click
        operation = "insert"
        enaAll()
        setText(nextId("MsMenu"))
    End Sub

    Private Sub btnUpdate_Click(sender As Object, e As EventArgs) Handles btnUpdate.Click
        If tbId.Text <> "" Then
            operation = "update"
            enaAll()
        Else
            MsgBox("Mohon Pilih satu data dahulu")
        End If
    End Sub

    Private Sub btnDelete_Click(sender As Object, e As EventArgs) Handles btnDelete.Click
        If tbId.Text <> "" Then
            Try
                openConn()
                query = "DELETE FROM MsMenu Where Id = '" & tbId.Text & "'"
                command = New SqlCommand(query, conn)
                command.ExecuteNonQuery()
                getMenu()
                setText()
                MsgBox("Data Berhasil diHapus")
            Catch ex As Exception
                MsgBox(ex.Message & ex.StackTrace)
            End Try
        Else
            MsgBox("Mohon Pilih satu data dahulu")
        End If
    End Sub

    Private Sub btnCancel_Click(sender As Object, e As EventArgs) Handles btnCancel.Click
        disAll()
    End Sub

    Private Sub btnSave_Click(sender As Object, e As EventArgs) Handles btnSave.Click
        If validation(Me) And customValidasi() Then
            If imgName = "" Then
                imgName = pbMenu.ImageLocation
            End If

            If operation = "insert" Then
                Try
                    openConn()
                    query = "Insert Into MsMenu(Name, Price, Photo, Carbo, Protein) Values('" & tbName.Text & "', '" & tbPrice.Text & "', '" & imgName & "', '" & tbCarbo.Text & "', '" & tbProtein.Text & "')"
                    command = New SqlCommand(query, conn)
                    command.ExecuteNonQuery()

                    If sourceLoc <> "" Then
                        My.Computer.FileSystem.CopyFile(sourceLoc, endLoc, True)
                    End If

                    MsgBox("Data has been Updated")
                Catch ex As Exception
                    MsgBox(ex.Message & ex.StackTrace)
                End Try

            Else
                Try
                    openConn()
                    query = "UPDATE MsMenu SET Name = '" & tbName.Text & "', Price = '" & tbPrice.Text & "', Photo = '" & imgName & "', Carbo = '" & tbCarbo.Text & "', Protein = '" & tbProtein.Text & "' WHERE Id = '" & tbId.Text & "'"
                    command = New SqlCommand(query, conn)
                    command.ExecuteNonQuery()

                    If sourceLoc <> "" Then
                        My.Computer.FileSystem.CopyFile(sourceLoc, endLoc, True)
                    End If

                    MsgBox("Data has been Updated")
                Catch ex As Exception
                    MsgBox(ex.Message & ex.StackTrace)
                End Try
            End If

            getMenu()
            disAll()
        End If
    End Sub

    Private Sub btnSearch_Click(sender As Object, e As EventArgs) Handles btnSearch.Click
        getMenu(tbSearch.Text)
    End Sub

    Private Sub cbSort_SelectedIndexChanged(sender As Object, e As EventArgs) Handles cbSort.SelectedIndexChanged
        getMenu(tbSearch.Text, cbSort.SelectedItem.ToString)
    End Sub

    Private Sub btnFile_Click(sender As Object, e As EventArgs) Handles btnFile.Click
        OpenFileDialog1.Filter = "Image Files (*.jpg, *.png, *.jpeg) | *.jpg; *.png; *.jpeg"
        If OpenFileDialog1.ShowDialog() = DialogResult.OK Then
            sourceLoc = OpenFileDialog1.FileName
            tbPhoto.Text = OpenFileDialog1.FileName
            pbMenu.ImageLocation = sourceLoc
            imgName = DateTime.Now.ToString("yyyyMMddHHmmss") & Path.GetExtension(OpenFileDialog1.FileName)
            endLoc = "MenuPic/" & imgName
        End If
    End Sub

    Private Sub FormMenu_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        NavAdmin.Show()
    End Sub

    Private Function customValidasi() As Boolean
        If tbName.Text.Length > 100 Then
            MsgBox("Nama tidak boleh lebih dari 100 karakter")
            Return False
        ElseIf Not IsNumeric(tbPrice.Text) Then
            MsgBox("Harga hanya boleh boleh angka")
            Return False
        ElseIf Not IsNumeric(tbCarbo.Text) Then
            MsgBox("Karbohidrat hanya boleh boleh angka")
            Return False
        ElseIf Not IsNumeric(tbProtein.Text) Then
            MsgBox("Protein hanya boleh boleh angka")
            Return False
        Else
            Return True
        End If
    End Function
End Class