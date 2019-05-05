Imports System.Data.SqlClient

Public Class FormMember
    Dim Operation As String

    Private Sub FormMember_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        cbSort.SelectedItem = "Id"
        disAll()
        getMember()
    End Sub

    Private Sub getMember(Optional keyword As String = "", Optional sort As String = "")
        Try
            openConn()
            query = "SELECT * FROM MsMember "
            If keyword <> "" Then
                query &= "WHERE Id LIKE '" & keyword & "' OR  Name LIKE '" & keyword & "' OR  Email LIKE '" & keyword & "' OR  Handphone LIKE '" & keyword & "' OR  JoinDate LIKE '" & keyword & "'"
            End If
            If sort <> "" Then
                query &= "Order By '" & sort & "' asc"
            End If
            adapter = New SqlDataAdapter(query, conn)
            dataset.Clear()
            adapter.Fill(dataset, "Member")
            With dg
                .DataSource = dataset
                .DataMember = "Member"
            End With
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try
    End Sub

    Private Sub FormMember_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        NavAdmin.Show()
    End Sub

    Private Sub enaAll()
        tbName.Enabled = True
        tbEmail.Enabled = True
        tbHp.Enabled = True
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
        btnSave.Visible = False
        btnCancel.Visible = False
        btnInsert.Visible = True
        btnUpdate.Visible = True
        btnDelete.Visible = True
        setText()
    End Sub

    Private Sub setText(Optional id As String = "",
                        Optional name As String = "",
                        Optional email As String = "",
                        Optional phone As String = "")
        tbId.Text = id
        tbName.Text = name
        tbEmail.Text = email
        tbHp.Text = phone
    End Sub

    Private Sub btnInsert_Click(sender As Object, e As EventArgs) Handles btnInsert.Click
        Operation = "insert"
        setText(nextId("MsMember"))
        enaAll()

    End Sub

    Private Sub btnUpdate_Click(sender As Object, e As EventArgs) Handles btnUpdate.Click
        If tbId.Text <> "" Then
            Operation = "update"
            enaAll()
        Else
            MsgBox("Mohon pilh satu data dahulu")
        End If
    End Sub

    Private Sub btnDelete_Click(sender As Object, e As EventArgs) Handles btnDelete.Click
        If tbId.Text <> "" Then
            Try
                openConn()
                query = "DELETE FROM MSMember WHERE Id = '" & tbId.Text & "'"
                command = New SqlCommand(query, conn)
                command.ExecuteNonQuery()
                getMember()
                setText()
                MsgBox("Data Berhasil terhapus")
            Catch ex As Exception
                MsgBox(ex.Message & ex.StackTrace)
            End Try
        Else
            MsgBox("Mohon pilh satu data dahulu")
        End If
    End Sub

    Private Sub btnCancel_Click(sender As Object, e As EventArgs) Handles btnCancel.Click
        disAll()
    End Sub

    Private Sub btnSave_Click(sender As Object, e As EventArgs) Handles btnSave.Click
        If validation(Me) And customValidasi() Then
            If Operation = "insert" Then
                Try
                    openConn()
                    query = "INSERT INTO MsMember(Name, Email, Handphone, JoinDate) VALUES('" & tbName.Text & "', '" & tbEmail.Text & "', '" & tbHp.Text & "', '" & DateTime.Now.ToString("yyyy-MM-dd") & "')"
                    command = New SqlCommand(query, conn)
                    command.ExecuteNonQuery()

                    getMember()
                    disAll()
                    MsgBox("Data has been inserted")
                Catch ex As Exception
                    MsgBox(ex.Message & ex.StackTrace)
                End Try
            Else
                Try
                    openConn()
                    query = "UPDATE MsMember SET Name = '" & tbName.Text & "', Email = '" & tbEmail.Text & "', Handphone = '" & tbHp.Text & "' WHERE Id = '" & tbId.Text & "'"
                    command = New SqlCommand(query, conn)
                    command.ExecuteNonQuery()

                    getMember()
                    disAll()
                    MsgBox("Data has been updated")
                Catch ex As Exception
                    MsgBox(ex.Message & ex.StackTrace)
                End Try
            End If
        End If
    End Sub

    Private Function customValidasi() As Boolean
        If tbName.Text.Length > 20 And tbName.Text.Length < 3 Then
            MsgBox("Nama harus terdiri dari 3 - 20 karakterr")
            Return False
        ElseIf Not IsNumeric(tbHp.Text) Then
            MsgBox("Nomor Handphone hanya berupa angka")
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

    Private Sub btnSearch_Click(sender As Object, e As EventArgs) Handles btnSearch.Click
        getMember(tbSearch.Text)
    End Sub

    Private Sub dg_CellClick(sender As Object, e As DataGridViewCellEventArgs) Handles dg.CellClick
        Dim i As Integer = dg.CurrentRow.Index

        With dg
            setText(.Item(0, i).Value, .Item(1, i).Value, .Item(2, i).Value, .Item(3, i).Value)
        End With
    End Sub

    Private Sub cbSort_SelectedIndexChanged(sender As Object, e As EventArgs) Handles cbSort.SelectedIndexChanged
        getMember(tbSearch.Text, cbSort.SelectedItem.ToString)
    End Sub
End Class