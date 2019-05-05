Imports System.Data.SqlClient

Public Class FormEmployee

    Dim operation As String

    Private Sub FormEmployee_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        cbSort.SelectedItem = "Id"
        disAll()
        getEmployee()
    End Sub

    Private Sub getEmployee(Optional keyword As String = "")
        Try
            openConn()
            query = "SELECT * FROM MsEmployee WHERE Position != 'admin'"
            If keyword <> "" Then
                query &= "AND (Id LIKE '%" & keyword & "%' OR Name LIKE '%" & keyword & "%' OR Email LIKE '%" & keyword & "%' OR Handphone LIKE '%" & keyword & "%' OR Position LIKE '%" & keyword & "%') "
            End If
            If cbSort.Text <> "" Then
                query &= "Order By '" & cbSort.Text & "' asc"
            End If
            adapter = New SqlDataAdapter(query, conn)
            dataset.Clear()
            adapter.Fill(dataset, "Employee")
            With dg
                .DataSource = dataset
                .DataMember = "Employee"
                .Columns(3).Visible = False
            End With
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try

    End Sub

    Private Sub enaAll()
        tbName.Enabled = True
        tbEmail.Enabled = True
        tbHp.Enabled = True
        cbPos.Enabled = True
        btnSave.Visible = True
        btnCancel.Visible = True
        btnInsert.Visible = False
        btnUpdate.Visible = False
        btnDelete.Visible = False
    End Sub

    Private Sub disAll()
        tbName.Enabled = False
        tbEmail.Enabled = False
        tbHp.Enabled = False
        cbPos.Enabled = False
        btnSave.Visible = False
        btnCancel.Visible = False
        btnInsert.Visible = True
        btnUpdate.Visible = True
        btnDelete.Visible = True
        setText()
    End Sub

    Private Sub FormEmployee_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        NavAdmin.Show()
    End Sub

    Private Sub dg_CellClick(sender As Object, e As DataGridViewCellEventArgs) Handles dg.CellClick
        Dim i As Integer = dg.CurrentRow.Index

        With dg
            setText(.Item(0, i).Value, .Item(1, i).Value, .Item(2, i).Value, .Item(4, i).Value, .Item(5, i).Value)
        End With
    End Sub

    Private Sub setText(Optional id As String = "",
                        Optional name As String = "",
                        Optional email As String = "",
                        Optional phone As String = "",
                        Optional pos As String = "")
        tbId.Text = id
        tbName.Text = name
        tbEmail.Text = email
        tbHp.Text = phone
        cbPos.SelectedItem = pos
    End Sub

    Private Sub btnInsert_Click(sender As Object, e As EventArgs) Handles btnInsert.Click
        operation = "insert"
        enaAll()
        setText(employeeId())
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
                query = "DELETE FROM MsEmployee WHERE Id = '" & tbId.Text & "'"
                command = New SqlCommand(query, conn)
                command.ExecuteNonQuery()
                getEmployee()
                setText()
                MsgBox("Data Berhasil di Hapus")
            Catch ex As Exception
                MsgBox(ex.Message & ex.StackTrace)
            End Try
        Else
            MsgBox("Mohon Pilih salah satu data")
        End If
    End Sub

    Private Sub btnCancel_Click(sender As Object, e As EventArgs) Handles btnCancel.Click
        disAll()
    End Sub

    Private Sub btnSave_Click(sender As Object, e As EventArgs) Handles btnSave.Click
        If validation(Me) And customValidasi() Then
            If operation = "insert" Then
                Try
                    openConn()
                    Dim pass As String = SHA512(tbName.Text.Substring(0, 1) & tbName.Text.Substring(tbName.Text.Length - 1, 1) & Mid(tbHp.Text, 3, 4))
                    query = "Insert Into MsEmployee(Id, Name, Email, Password, Handphone, Position) Values('" & tbId.Text & "', '" & tbName.Text & "', '" & tbEmail.Text & "', '" & pass & "', '" & tbHp.Text & "', '" & cbPos.SelectedItem & "')"
                    command = New SqlCommand(query, conn)
                    command.ExecuteNonQuery()

                    MsgBox("Data has been Inserted")
                Catch ex As Exception
                    MsgBox(ex.Message & ex.StackTrace)
                End Try

            Else
                Try
                    openConn()
                    query = "UPDATE MsEmployee SET Name = '" & tbName.Text & "', Email = '" & tbEmail.Text & "', Handphone = '" & tbHp.Text & "', Position = '" & cbPos.SelectedItem & "' WHERE Id = '" & tbId.Text & "'"
                    command = New SqlCommand(query, conn)
                    command.ExecuteNonQuery()

                    MsgBox("Data has been Updated")
                Catch ex As Exception
                    MsgBox(ex.Message & ex.StackTrace)
                End Try
            End If

            getEmployee()
            disAll()
        End If
    End Sub

    Private Sub btnSearch_Click(sender As Object, e As EventArgs) Handles btnSearch.Click
        getEmployee(tbSearch.Text)
    End Sub

    Private Sub cbSort_SelectedIndexChanged(sender As Object, e As EventArgs) Handles cbSort.SelectedIndexChanged
        getEmployee(tbSearch.Text)
    End Sub

    Private Function customValidasi() As Boolean
        If tbName.Text.Length > 20 And tbName.Text.Length < 3 Then
            MsgBox("Nama harus terdiri dari 3 - 20 karakter")
            Return False
        ElseIf Not IsNumeric(tbHp.Text) Then
            MsgBox("Nomor Handphone hanya boleh boleh angka")
            Return False
        ElseIf tbHp.Text.Length > 12 And tbHp.Text.Length < 11 Then
            MsgBox("Nomor Handphone harus terdiri dari 11 atau 12 karakter")
            Return False
        ElseIf tbHp.Text.Substring(0, 2) <> "08" Then
            MsgBox("Nomor Handphone diawali dengan 08")
            Return False
        Else
            Return True
        End If
    End Function
End Class